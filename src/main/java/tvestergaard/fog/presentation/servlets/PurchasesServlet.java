package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.purchases.PurchaseColumn;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.TableControls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.constraints.OrderDirection.DESC;

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
        if (authentication.redirect(resp))
            return;

        Customer customer = authentication.getCustomer();

        TableControls<PurchaseColumn> controls = new TableControls<>(req, where(eq(PurchaseColumn.CUSTOMER, customer.getId())).order(PurchaseColumn.CREATED_AT, DESC));
        controls.add(PurchaseColumn.OFFER, TableControls.Type.INT);
        controls.add(PurchaseColumn.EMPLOYEE_NAME, TableControls.Type.TEXT);
        controls.add(PurchaseColumn.PURCHASE_PRICE, TableControls.Type.INT);
        controls.add(PurchaseColumn.CREATED_AT, TableControls.Type.TEXT);

        req.setAttribute("context", ".");
        req.setAttribute("title", "Mine køb");
        req.setAttribute("navigation", "purchases");
        req.setAttribute("customer", customer);
        req.setAttribute("purchases", purchaseFacade.get(controls.constraints()));
        req.getRequestDispatcher("/WEB-INF/purchases.jsp").forward(req, resp);
    }
}
