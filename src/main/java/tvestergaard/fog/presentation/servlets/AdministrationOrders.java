package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.orders.OrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdministrationOrders", urlPatterns = "/administration/orders")
public class AdministrationOrders extends HttpServlet
{

    private final OrderFacade       facade     = new OrderFacade();
    private final CommandDispatcher dispatcher = new CommandDispatcher();

    public AdministrationOrders()
    {
        dispatcher.get(null, new ShowOrderTableCommand());
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    private class ShowOrderTableCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            request.setAttribute("title", "Ordre");
            request.setAttribute("orders", facade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_orders.jsp").forward(request, response);
        }
    }
}
