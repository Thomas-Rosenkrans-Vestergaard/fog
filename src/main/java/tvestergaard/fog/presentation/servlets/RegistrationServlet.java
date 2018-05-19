package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.customers.CustomerError;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.CustomerValidatorException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

    /**
     * Displays the /registration page, where customers can see their order history.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("context", ".");
        req.setAttribute("title", "Registrer");
        req.setAttribute("navigation", "registration");
        req.setAttribute("csrf", csrf(req));
        req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
    }

    /**
     * Processes the information sent by the customer on the /registration page.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!vefiry(req)) {
            resp.sendRedirect("registration");
            return;
        }

        if (!parameters.isPresent("name") ||
                !parameters.isPresent("address") ||
                !parameters.isPresent("email") ||
                !parameters.isPresent("phone") ||
                !parameters.isPresent("password")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect("registration");
            return;
        }

        try {

            Customer customer = customerFacade.register(
                    parameters.value("name"),
                    parameters.value("address"),
                    parameters.value("email"),
                    parameters.value("phone"),
                    parameters.value("password"),
                    true);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            notifications.success("Kundekontoen blev oprettet.");
            notifications.info("Før du kan benytte din konto, skal du besøge linket tilsendt til din mail.");
            resp.sendRedirect("profile");

        } catch (CustomerValidatorException e) {
            for (CustomerError error : e.getErrors())
                notifications.error(getError(error));
            resp.sendRedirect("registration");
        }
    }
}
