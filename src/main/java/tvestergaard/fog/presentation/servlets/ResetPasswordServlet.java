package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.ExpiredTokenException;
import tvestergaard.fog.logic.customers.IncorrectTokenException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = new CustomerFacade();

    /**
     * Displays a page, where customers can reset their password.
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

        if (!parameters.isInt("tokenId") || !parameters.isPresent("tokenSecret")) {
            notifications.error("Ulovlige parametre.");
            resp.sendRedirect("reset-password");
            return;
        }

        req.setAttribute("title", "Gensæt adgangskode");
        req.setAttribute("tokenId", parameters.getInt("tokenId"));
        req.setAttribute("tokenSecret", parameters.value("tokenSecret"));
        req.getRequestDispatcher("/WEB-INF/reset-password.jsp").forward(req, resp);
    }

    /**
     * Accepts the new password.
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

        if (!parameters.isInt("tokenId") || !parameters.isPresent("tokenSecret") || !parameters.isPresent("password")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect(req.getQueryString());
        }

        try {
            customerFacade.resetPassword(parameters.getInt("tokenId"), parameters.value("tokenSecret"), parameters.value("password"));
            notifications.success("Din adgangskode er nu ændret.");
            resp.sendRedirect("account");
        } catch (IncorrectTokenException e) {
            notifications.error("Ukorrekt token.");
            resp.sendRedirect(req.getQueryString());
        } catch (ExpiredTokenException e) {
            notifications.error("Token udløbet.");
            resp.sendRedirect(req.getQueryString());
        }
    }
}
