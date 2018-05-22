package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeColumn;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.logic.employees.EmployeeError;
import tvestergaard.fog.logic.employees.EmployeeFacade;
import tvestergaard.fog.logic.employees.EmployeeValidatorException;
import tvestergaard.fog.logic.employees.UnknownEmployeeException;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.TableControls;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.logic.employees.EmployeeError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/employees")
public class AdministrationEmployeesServlet extends AdministrationServlet
{
    private final EmployeeFacade             facade = Facades.employeeFacade;
    private final Map<EmployeeError, String> errors = new HashMap<>();

    public AdministrationEmployeesServlet()
    {
        dispatcher.get(null, new AdministrationEmployeesServlet.ShowTableCommand());
        dispatcher.get("create", new AdministrationEmployeesServlet.ShowCreateCommand());
        dispatcher.get("update", new AdministrationEmployeesServlet.ShowUpdateCommand());
        dispatcher.post("create", new AdministrationEmployeesServlet.HandleCreateCommand());
        dispatcher.post("update", new AdministrationEmployeesServlet.HandleUpdateCommand());

        errors.put(NAME_EMPTY, "Navnet der blev sendt var tomt.");
        errors.put(NAME_LONGER_THAN_255, "Navnet der blev sendt var længere end 255 tegn.");
        errors.put(USERNAME_EMPTY, "Brugernavnet der blev sendt var tomt.");
        errors.put(USERNAME_LONGER_THAN_255, "Brugernavnet der sendt var længere end 255 tegn.");
        errors.put(USERNAME_TAKEN, "Brugernavnet er allerede i brug.");
        errors.put(PASSWORD_SHORTER_THAN_4, "Adgangskoden der blev sendt var for kort (4 tegn).");
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.HEAD_OF_CENTER)) {
            notifications.error("Du skal være centerchef for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_employees");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<EmployeeColumn> controls = new TableControls<>(request);
            controls.add(EmployeeColumn.NAME, TableControls.Type.TEXT);
            controls.add(EmployeeColumn.USERNAME, TableControls.Type.TEXT);
            controls.add(EmployeeColumn.ACTIVE, TableControls.Type.BOOLEAN);
            controls.add(EmployeeColumn.CREATED_AT, TableControls.Type.TIMESTAMP);
            notifications(request);
            request.setAttribute("title", "Medarbejdere");
            request.setAttribute("employees", facade.get(controls.constraints()));
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/show_employees.jsp").forward(request, response);
        }
    }

    class ShowUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id")) {
                notifications.error("No identifier provided.");
                response.sendRedirect("employees");
                return;
            }

            Employee employee = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (employee == null) {
                notifications.error("Unknown employee.");
                response.sendRedirect("employees");
                return;
            }

            request.setAttribute("title", "Opdater medarbejdere");
            request.setAttribute("employee", employee);
            request.setAttribute("roles", Role.values());
            request.getRequestDispatcher("/WEB-INF/administration/update_employee.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id") ||
                    !parameters.isPresent("name") ||
                    !parameters.isPresent("username") ||
                    !parameters.isEnums("roles", Role.class) ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("employees");
                return;
            }

            try {
                facade.update(parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("username"),
                        parameters.getEnums("roles", Role.class),
                        parameters.value("password"),
                        parameters.getBoolean("active"));

                notifications.success("Medarbejderen blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (UnknownEmployeeException e) {
                notifications.error("Kunne ikke finde medarbejderen.");
                response.sendRedirect("employees");
            } catch (EmployeeValidatorException e) {
                for (EmployeeError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
            }
        }
    }

    private class ShowCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Opret medarbejder");
            request.setAttribute("roles", Role.values());
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/create_employee.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("name") ||
                    !parameters.isPresent("username") ||
                    !parameters.isPresent("password") ||
                    !parameters.isEnums("roles", Role.class) ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("employees");
                return;
            }

            try {
                Employee employee = facade.register(parameters.value("name"),
                        parameters.value("username"),
                        parameters.value("password"),
                        parameters.getEnums("roles", Role.class),
                        parameters.getBoolean("active"));

                notifications.success("Medarbejderen blev oprettet.");
                response.sendRedirect("?action=update&id=" + employee.getId());

            } catch (EmployeeValidatorException e) {
                for (EmployeeError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
        }
    }
}
