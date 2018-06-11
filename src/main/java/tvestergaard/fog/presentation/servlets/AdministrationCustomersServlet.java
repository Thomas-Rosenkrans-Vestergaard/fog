package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.customers.UnknownCustomerException;
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

import static tvestergaard.fog.logic.customers.CustomerError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/administration/customers")
public class AdministrationCustomersServlet extends AdministrationServlet
{

    private final CustomerFacade             facade = Facades.customerFacade;
    private final Map<CustomerError, String> errors = new HashMap<>();

    public AdministrationCustomersServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.post("create", new HandleCreateCommand());
        dispatcher.post("activate", new HandleActivateCommand());
        dispatcher.post("deactivate", new HandleDeactivateCommand());

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
            request.setAttribute("csrf", csrf(request));
            request.setAttribute("customers", facade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_customers.jsp").forward(request, response);
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

    private class HandleActivateCommand implements Command
    {
        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);


            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=create");
                return;
            }

            if (!parameters.isPresent("id")) {
                notifications.error("No id provided.");
                response.sendRedirect("customers");
                return;
            }

            try {
                facade.activate(parameters.getInt("id"));
                notifications.success("Kunden blev aktiveret.");
                response.sendRedirect("customers");
            } catch (UnknownCustomerException e) {
                notifications.error("Ukendt kunde.");
                response.sendRedirect("customers");
            }
        }
    }

    private class HandleDeactivateCommand implements Command
    {
        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);


            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=create");
                return;
            }

            if (!parameters.isPresent("id")) {
                notifications.error("No id provided.");
                response.sendRedirect("customers");
                return;
            }

            try {
                facade.deactivate(parameters.getInt("id"));
                notifications.success("Kunden blev inaktiveret.");
                response.sendRedirect("customers");
            } catch (UnknownCustomerException e) {
                notifications.error("Ukendt kunde.");
                response.sendRedirect("customers");
            }
        }
    }
}
