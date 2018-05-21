package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseColumn;
import tvestergaard.fog.data.purchases.bom.Bom;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.TableControls;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.purchases.PurchaseColumn.ID;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/purchases")
public class AdministrationPurchases extends AdministrationServlet
{

    private final PurchaseFacade purchaseFacade = Facades.purchaseFacade;

    public AdministrationPurchases()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("show", new ShowPurchaseCommand());
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        req.setAttribute("navigation", "administration_purchases");
        return true;
    }


    private class ShowTableCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<PurchaseColumn> controls = new TableControls<>(request, PurchaseColumn.class, PurchaseColumn.SEARCH);
            notifications(request);
            request.setAttribute("title", "Køb");
            request.setAttribute("purchases", purchaseFacade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_purchases.jsp").forward(request, response);
        }
    }

    private class ShowPurchaseCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param req  The request.
         * @param resp The response.
         */
        @Override public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
        {
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

            Bom bom = purchaseFacade.getBom(purchase.getBomId());

            req.setAttribute("context", "..");
            req.setAttribute("title", "Køb");
            req.setAttribute("navigation", "administration_purchases");
            req.setAttribute("purchase", purchase);
            req.setAttribute("bom", bom);
            req.getRequestDispatcher("/WEB-INF/administration/show_purchase.jsp").forward(req, resp);
        }
    }
}
