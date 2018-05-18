package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.offers.OfferNotOpenException;
import tvestergaard.fog.logic.offers.UnknownOfferException;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.tokens.ExpiredTokenException;
import tvestergaard.fog.logic.tokens.IncorrectTokenException;
import tvestergaard.fog.presentation.Notifications;
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

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/api")
public class ApiServlet extends HttpServlet
{

    private final CommandDispatcher dispatcher  = new CommandDispatcher();
    private final OrderFacade       orderFacade = Facades.orderFacade;
    private final OfferFacade       offerFacade = Facades.offerFacade;

    public ApiServlet()
    {
        dispatcher.get("getNumberOfNewOrders", new GetNumberOfNewOrdersCommand());
        dispatcher.get("getNumberOfNewOffers", new GetNumberOfNewOffersCommand());
        dispatcher.get("accept-offer", new AcceptOfferCommand());
        dispatcher.get("reject-offer", new RejectOfferCommand());
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

    private class AcceptOfferCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("tokenId") || !parameters.isPresent("tokenSecret") || !parameters.isPresent("offer")) {
                notifications.error("Not enough token information");
                response.sendRedirect("offers");
                return;
            }

            try {
                Facades.offerFacade.accept(parameters.getInt("offer"), parameters.getInt("tokenId"), parameters.value("tokenSecret"));
                notifications.success("Tilbudet blev accepteret.");
                response.sendRedirect("offers");
                return;
            } catch (ExpiredTokenException e) {
                notifications.error("Udløbet token.");
            } catch (IncorrectTokenException e) {
                notifications.error("Ukorrekt token.");
            } catch (OfferNotOpenException e) {
                notifications.error("Tilbud ikke åbent.");
            } catch (InsufficientPermissionsException e) {
                notifications.error("Medarbejder fejl.");
            } catch (UnknownOfferException e) {
                notifications.error("Ukendt tilbud.");
            } catch (InactiveCustomerException e) {
                notifications.error("Inaktiv kunde.");
            }

            response.sendRedirect("offers");
        }
    }

    private class RejectOfferCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("tokenId") || !parameters.isPresent("tokenSecret") || !parameters.isPresent("offer")) {
                notifications.error("Not enough token information");
                response.sendRedirect("offers");
                return;
            }

            try {
                offerFacade.reject(parameters.getInt("offer"), parameters.getInt("tokenId"), parameters.value("tokenSecret"));
                notifications.success("Tilbudet blev afvist.");
                response.sendRedirect("offers");
                return;

            } catch (IncorrectTokenException e) {
                notifications.error("Ukorrekt token.");
            } catch (ExpiredTokenException e) {
                notifications.error("Token udløbet.");
            } catch (UnknownOfferException e) {
                notifications.error("Det tilbud eksisterer ikke.");
            } catch (OfferNotOpenException e) {
                notifications.error("Tilbuddet er ikke åbent.");
            }

            response.sendRedirect("authenticate");
        }
    }
}
