package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.presentation.Authentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.constraints.OrderDirection.DESC;
import static tvestergaard.fog.data.offers.OfferColumn.CREATED_AT;
import static tvestergaard.fog.data.orders.OrderColumn.CUSTOMER;

@WebServlet(urlPatterns = "/orders")
public class OrdersServlet extends HttpServlet
{

    private final OrderFacade orderFacade = Facades.orderFacade;

    /**
     * Displays the /orders page, where customers can see their order history.
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

        Customer customer = authentication.getCustomer();

        req.setAttribute("context", ".");
        req.setAttribute("title", "Mine ordre");
        req.setAttribute("navigation", "orders");
        req.setAttribute("customer", customer);
        req.setAttribute("orders", orderFacade.get(where(eq(CUSTOMER, customer.getId())).order(CREATED_AT, DESC)));
        req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
    }
}
