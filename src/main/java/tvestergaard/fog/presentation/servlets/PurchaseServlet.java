package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.construction.GarageConstructionSummary;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
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
import static tvestergaard.fog.data.purchases.PurchaseColumn.ID;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/purchase")
public class PurchaseServlet extends HttpServlet
{

    private final PurchaseFacade     purchaseFacade     = Facades.purchaseFacade;
    private final ConstructionFacade constructionFacade = Facades.constructionFacade;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Customer      customer      = authentication.getCustomer();
        Notifications notifications = notifications(req);
        Parameters    parameters    = new Parameters(req);
        if (!parameters.isInt("id")) {
            notifications.error("No id.");
            resp.sendRedirect("purchases");
            return;
        }

        Purchase purchase = purchaseFacade.first(where(eq(ID, parameters.getInt("id"))));

        if (purchase == null) {
            notifications.error("No purchase with provided id.");
            resp.sendRedirect("purchases");
            return;
        }

        GarageConstructionSummary summary = constructionFacade.construct(purchase.getOffer().getOrder());

        req.setAttribute("context", ".");
        req.setAttribute("title", "Mine køb");
        req.setAttribute("navigation", "purchases");
        req.setAttribute("customer", customer);
        req.setAttribute("purchase", purchase);
        req.setAttribute("summary", summary);
        req.getRequestDispatcher("/WEB-INF/purchase.jsp").forward(req, resp);
    }
}
