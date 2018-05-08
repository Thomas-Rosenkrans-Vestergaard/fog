package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.customers.UnknownEmailException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

    /**
     * Displays a page, where customers can request to reset their password.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("context", ".");
        req.setAttribute("title", "Glemt password");
        req.setAttribute("navigation", "forgot-password");
        req.getRequestDispatcher("/WEB-INF/forgot-password.jsp").forward(req, resp);
    }

    /**
     * Accepts the data posted from /forgot-password.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends  to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request for the POST could not be handled
     */
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!parameters.isPresent("email")) {
            notifications.error("Manglende email adresse.");
            resp.sendRedirect("forgot-password");
            return;
        }

        try {
            String email = parameters.value("email");
            customerFacade.sendPasswordReset(email);
            notifications.success("En email er blevet sendt.");
            resp.sendRedirect("forgot-password");
        } catch (UnknownEmailException e) {
            notifications.error("Den angivede maøiladresse er ikke registreret i systemet.");
            resp.sendRedirect("forgot-password");
        } catch (InactiveCustomerException e) {
            notifications.error("Kunden med den angivede mailadresse er ikke aktiv, og kan derfor ikke anmode om et nyt password.");
            resp.sendRedirect("forgot-password");
        }
    }
}
