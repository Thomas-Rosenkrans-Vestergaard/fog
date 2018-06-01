package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.UnknownCustomerException;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

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
        req.setAttribute("customer", authentication.getCustomer());
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Notifications notifications = notifications(req);
        String        action        = req.getParameter("action");

        if ("resend-confimation".equals(action)) {

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
    }
}
