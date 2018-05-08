package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
import tvestergaard.fog.presentation.Authentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.orders.OrderColumn.CUSTOMER;

@WebServlet(urlPatterns = "/purchases")
public class PurchasesServlet extends HttpServlet
{

    private final PurchaseFacade purchaseFacade = Facades.purchaseFacade;

    /**
     * Displays the /purchases page, where customers can see their purchase history.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp, "purchases"))
            return;

        Customer customer = authentication.getCustomer();

        req.setAttribute("context", ".");
        req.setAttribute("title", "Mine køb");
        req.setAttribute("navigation", "purchases");
        req.setAttribute("customer", customer);
        req.setAttribute("orders", purchaseFacade.get(where(eq(CUSTOMER, customer.getId()))));
        req.getRequestDispatcher("/WEB-INF/purchases.jsp").forward(req, resp);
    }
}
