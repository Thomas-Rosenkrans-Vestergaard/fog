package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.customers.UnknownCustomerException;
import tvestergaard.fog.logic.customers.CustomerError;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.CustomerValidatorException;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.ID;
import static tvestergaard.fog.logic.customers.CustomerError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet
{

    private final CustomerFacade             customerFacade = Facades.customerFacade;
    private final Map<CustomerError, String> errors         = new HashMap<>();

    public ProfileServlet()
    {
        errors.put(NAME_EMPTY, "Navnet der blev sendt var tomt.");
        errors.put(NAME_LONGER_THAN_255, "Navnet der blev sendt var længere end 255 tegn.");
        errors.put(ADDRESS_EMPTY, "Adressen der blev sendt var tomt.");
        errors.put(ADDRESS_LONGER_THAN_255, "Adressen der sendt var længere end 255 tegn.");
        errors.put(EMAIL_INVALID, "Mailadressen der blev sendt var ikke formateret korrekt.");
        errors.put(EMAIL_LONGER_THAN_255, "Mailadressen der blev sendt var længere end 255 tegn.");
        errors.put(EMAIL_TAKEN, "Mailadressen der blev sendt er allerede taget.");
        errors.put(PHONE_EMPTY, "Telefonnummeret der blev sendt var tomt.");
        errors.put(PHONE_LONGER_THAN_30, "Telefonnummeret der blev sendt var længere end 255 tegn.");
    }

    /**
     * Displays the /profile page, where customers can see their order history.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        req.setAttribute("context", ".");
        req.setAttribute("title", "Profil");
        req.setAttribute("navigation", "profile");
        req.setAttribute("customer", customerFacade.first(where(eq(ID, authentication.getCustomer().getId()))));
        req.setAttribute("csrf", csrf(req));
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Notifications notifications = notifications(req);
        Parameters    parameters    = new Parameters(req);

        if (!parameters.isPresent("action")) {
            notifications.error("Unknown action");
            resp.sendRedirect("profile");
            return;
        }

        String action = parameters.value("action");

        if ("resend-confirmation".equals(action)) {

            try {
                customerFacade.resendConfirmation(authentication.getCustomer().getId());
                notifications.success("En ny bekræftelsesemail er blevet sendt.");
                resp.sendRedirect("profile");
            } catch (UnknownCustomerException e) {
                notifications.error("Det skete en fejl.");
                resp.sendRedirect("profile");
                return;
            }

            return;
        }

        if ("update-information".equals(action)) {

            if (!parameters.isPresent("name") ||
                    !parameters.isPresent("address") ||
                    !parameters.isPresent("phone") ||
                    !parameters.isPresent("email")) {
                notifications.error("Manglende informationer.");
                resp.sendRedirect("profile");
                return;
            }

            try {
                customerFacade.update(
                        authentication.getCustomer().getId(),
                        parameters.value("name"),
                        parameters.value("address"),
                        parameters.value("email"),
                        parameters.value("phone"));

                notifications.success("Dine informationer var opdateret.");
                resp.sendRedirect("profile");

            } catch (CustomerValidatorException e) {
                for (CustomerError error : e.getErrors())
                    notifications.error(errors.get(error));
                resp.sendRedirect("profile");
            } catch (UnknownCustomerException e) {
                notifications.error("Unknown customer.");
                resp.sendRedirect("profile");
            }
        }
    }
}
