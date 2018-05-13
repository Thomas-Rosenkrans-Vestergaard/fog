package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.commands.Command;
import tvestergaard.fog.presentation.servlets.commands.CommandDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/api")
public class AjaxServlet extends HttpServlet
{

    private final CommandDispatcher dispatcher  = new CommandDispatcher();
    private final OrderFacade       orderFacade = Facades.orderFacade;
    private final OfferFacade       offerFacade = Facades.offerFacade;

    public AjaxServlet()
    {
        dispatcher.get("getNumberOfNewOrders", new GetNumberOfNewOrdersCommand());
        dispatcher.get("getNumberOfNewOffers", new GetNumberOfNewOffersCommand());
    }

    /**
     * Functions as an API endpoint for retrieving information.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        dispatcher.dispatch(req, resp);
    }

    private class GetNumberOfNewOrdersCommand implements Command
    {

        /**
         * Displays the number of orders that are both active and has no offers.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            writer.write(String.format("{\"orders\": %d}", orderFacade.getNumberOfNewOrders()));
            writer.flush();
            writer.close();
        }
    }

    private class GetNumberOfNewOffersCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters parameters = new Parameters(request);

            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");

            if (!parameters.isPresent("customer")) {
                writer.write(String.format("{\"error\": \"No customer id provided.\"}"));
                writer.flush();
                writer.close();
                return;
            }

            writer.write(String.format("{\"offers\": %d}", offerFacade.getNumberOfOpenOffers(parameters.getInt("customer"))));
            writer.flush();
            writer.close();
        }
    }
}
