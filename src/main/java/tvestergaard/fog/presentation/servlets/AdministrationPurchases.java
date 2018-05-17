package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.purchases.PurchaseColumn;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/purchases")
public class AdministrationPurchases extends AdministrationServlet
{

    private final PurchaseFacade purchaseFacade = Facades.purchaseFacade;

    public AdministrationPurchases()
    {
        dispatcher.get(null, new ShowTableCommand());
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
}
