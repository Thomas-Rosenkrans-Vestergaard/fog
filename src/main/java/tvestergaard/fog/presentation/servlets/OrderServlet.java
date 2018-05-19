package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.offers.OfferColumn;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.OrderFacade;
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
import static tvestergaard.fog.data.orders.OrderColumn.ID;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet
{

    private final OrderFacade orderFacade = Facades.orderFacade;
    private final OfferFacade offerFacade = Facades.offerFacade;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters     parameters     = new Parameters(req);
        Notifications  notifications  = notifications(req);
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Customer customer = authentication.getCustomer();

        if (!parameters.isInt("id")) {
            notifications.error("No order id.");
            resp.sendRedirect("orders");
            return;
        }

        Order order = orderFacade.first(where(eq(ID, parameters.getInt("id"))));

        if (order == null) {
            notifications.error("Unknown order.");
            resp.sendRedirect("orders");
            return;
        }

        if (order.getCustomer().getId() != customer.getId()) {
            notifications.error("Denne ordre tilhører ikke dig.");
            resp.sendRedirect("orders");
            return;
        }

        req.setAttribute("context", ".");
        req.setAttribute("title", "Mine ordre");
        req.setAttribute("navigation", "orders");
        req.setAttribute("order", order);
        req.setAttribute("offers", offerFacade.get(where(eq(OfferColumn.ORDER, order.getId()))));
        req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
    }
}
