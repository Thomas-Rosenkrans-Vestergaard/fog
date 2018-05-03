package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.orders.OrderColumn.CUSTOMER;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/offers")
public class OffersServlet extends HttpServlet
{

    private final OfferFacade offerFacade = Facades.offerFacade;

    /**
     * Displays the /offers page, where customers can see their offer history.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);

        if (!authentication.isCustomer()) {
            notifications.error("Du skal være logget ind for at tilgå denne side.");
            resp.sendRedirect("account");
            return;
        }

        Customer customer = authentication.getCustomer();

        req.setAttribute("title", "Mine ordre");
        req.setAttribute("navigation", "offers");
        req.setAttribute("customer", customer);
        req.setAttribute("offers", offerFacade.get(where(eq(CUSTOMER, customer.getId()))));
        req.getRequestDispatcher("/WEB-INF/offers.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String action = req.getParameter("action");

        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!parameters.isInt("offer")) {
            notifications.error("Missing offer id.");
            resp.sendRedirect("offers");
            return;
        }

        int order = parameters.getInt("offer");

        if ("accept".equals(action)) {

        }

        if ("reject".equals(action)) {

        }

        throw new UnsupportedOperationException();
    }
}
