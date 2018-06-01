package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.logic.customers.CustomerError;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.CustomerValidatorException;
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
import static tvestergaard.fog.logic.customers.CustomerError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;
import static tvestergaard.fog.presentation.PresentationFunctions.verify;

@WebServlet(urlPatterns = "/administration/customers")
public class AdministrationCustomersServlet extends AdministrationServlet
{

    private final CustomerFacade             facade = Facades.customerFacade;
    private final Map<CustomerError, String> errors = new HashMap<>();

    public AdministrationCustomersServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("create", new HandleCreateCommand());
        dispatcher.post("update", new HandleUpdateCommand());

        errors.put(NAME_EMPTY, "Navnet der blev sendt var tomt.");
        errors.put(NAME_LONGER_THAN_255, "Navnet der blev sendt var længere end 255 tegn.");
        errors.put(ADDRESS_EMPTY, "Adressen der blev sendt var tomt.");
        errors.put(ADDRESS_LONGER_THAN_255, "Adressen der sendt var længere end 255 tegn.");
        errors.put(EMAIL_INVALID, "Mailadressen der blev sendt var ikke formateret korrekt.");
        errors.put(EMAIL_LONGER_THAN_255, "Mailadressen der blev sendt var længere end 255 tegn.");
        errors.put(EMAIL_TAKEN, "Mailadressen der blev sendt er allerede taget.");
        errors.put(PHONE_EMPTY, "Telefonnummeret der blev sendt var tomt.");
        errors.put(PHONE_LONGER_THAN_30, "Telefonnummeret der blev sendt var længere end 255 tegn.");
        errors.put(PASSWORD_SHORTER_THAN_4, "Adgangskoden der blev sendt var for kort (4 tegn).");
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        req.setAttribute("navigation", "administration_customers");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<CustomerColumn> controls = new TableControls<>(request);
            controls.add(CustomerColumn.NAME, TableControls.Type.TEXT);
            controls.add(CustomerColumn.ADDRESS, TableControls.Type.TEXT);
            controls.add(CustomerColumn.EMAIL, TableControls.Type.TEXT);
            controls.add(CustomerColumn.PHONE, TableControls.Type.TEXT);
            controls.add(CustomerColumn.ACTIVE, TableControls.Type.BOOLEAN);
            controls.add(CustomerColumn.CREATED_AT, TableControls.Type.TIMESTAMP);

            notifications(request);
            request.setAttribute("title", "Kunder");
            request.setAttribute("customers", facade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_customers.jsp").forward(request, response);
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
                response.sendRedirect("customers");
                return;
            }

            Customer customer = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (customer == null) {
                notifications.error("Unknown customer.");
                response.sendRedirect("customers");
                return;
            }

            request.setAttribute("title", "Opdater kunder");
            request.setAttribute("csrf", csrf(request));
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/WEB-INF/administration/update_customer.jsp").forward(request, response);
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
                    !parameters.isPresent("address") ||
                    !parameters.isPresent("email") ||
                    !parameters.isPresent("phone") ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("customers");
                return;
            }

            try {
                facade.update(parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("address"),
                        parameters.value("email"),
                        parameters.value("phone"),
                        parameters.value("password"),
                        parameters.getBoolean("active"));

                notifications.success("Kunden blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (CustomerValidatorException e) {
                for (CustomerError error : e.getErrors())
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
            request.setAttribute("title", "Opret kunde");
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/create_customer.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=create");
                return;
            }

            if (!parameters.isPresent("name") ||
                    !parameters.isPresent("address") ||
                    !parameters.isPresent("email") ||
                    !parameters.isPresent("phone") ||
                    !parameters.isPresent("password")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("customers");
                return;
            }

            try {
                Customer customer = facade.register(parameters.value("name"),
                        parameters.value("address"),
                        parameters.value("email"),
                        parameters.value("phone"),
                        parameters.value("password"));

                notifications.success("Kunden blev oprettet.");
                response.sendRedirect("?action=update&id=" + customer.getId());

            } catch (CustomerValidatorException e) {
                for (CustomerError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
        }
    }
}
