package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/authenticate")
public class AuthenticationServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

    /**
     * Displays the /authenticate page, where customers can see their order history.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("context", ".");
        req.setAttribute("title", "Log ind");
        req.setAttribute("navigation", "authenticate");
        req.getRequestDispatcher("/WEB-INF/authenticate.jsp").forward(req, resp);
    }

    /**
     * Processes the information sent by the customer on the /authenticate page.
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

        if (!parameters.isPresent("email") || !parameters.isPresent("password")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect("authenticate");
            return;
        }

        try {
            Customer customer = customerFacade.authenticate(parameters.value("email"), parameters.value("password"));

            if (customer == null) {
                notifications.error("Ukorrekte akkreditiver.");
                resp.sendRedirect("authenticate");
                return;
            }

            String from = req.getParameter("from");

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            notifications.success("Du er nu logget ind.");
            resp.sendRedirect(from == null ? "profile" : URLDecoder.decode(from, "UTF-8"));

        } catch (InactiveCustomerException e) {
            notifications.error("Denne konto er inaktiv.");
            resp.sendRedirect("authenticate");
        }
    }
}
