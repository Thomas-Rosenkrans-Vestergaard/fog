package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.tokens.ExpiredTokenException;
import tvestergaard.fog.logic.tokens.IncorrectTokenException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/confirm-membership")
public class ConfirmMembershipServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

    /**
     * Displays the /confirm-membership page, where customers can confirm their membership from the email they received.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!parameters.isInt("id") || !parameters.isPresent("token")) {
            notifications.error("Bad challenge token.");
            resp.sendRedirect("registration");
            return;
        }

        try {
            customerFacade.confirm(parameters.getInt("id"), parameters.value("token"));
            notifications.success("Din kundekonto blev bekræftet.");
            resp.sendRedirect("authenticate");
        } catch (IncorrectTokenException e) {
            notifications.error("The token was incorrect.");
            resp.sendRedirect("profile");
        } catch (ExpiredTokenException e) {
            notifications.error("The token was expired.");
            resp.sendRedirect("profile");
        }
    }
}
