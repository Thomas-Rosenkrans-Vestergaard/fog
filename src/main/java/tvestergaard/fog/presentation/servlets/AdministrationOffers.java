package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.offers.OfferError;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.offers.OfferValidatorException;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.offers.OfferColumn.ID;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/offers")
public class AdministrationOffers extends HttpServlet
{

    private final OfferFacade             offerFacade = Facades.offerFacade;
    private final CommandDispatcher       dispatcher  = new CommandDispatcher();
    private final Map<OfferError, String> errors      = new HashMap();

    public AdministrationOffers()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.post("create", new HandleCreateCommand());

        errors.put(OfferError.NEGATIVE_PRICE, "Prisen må ikke være negativ.");
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);
        if (!authentication.isEmployee()) {
            notifications.error("Du skal være logged ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("login");
            return;
        }

        dispatcher.dispatch(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);
        if (!authentication.isEmployee()) {
            notifications.error("Du skal være logged ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("login");
            return;
        }

        dispatcher.dispatch(req, resp);
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            request.setAttribute("title", "Tilbud");
            request.setAttribute("offers", offerFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_offers.jsp").forward(request, response);
        }
    }

    private class ShowCreateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);
            if (!parameters.isInt("order")) {
                notifications.error("Illegal arguments.");
                response.sendRedirect("offers");
                return;
            }

            request.setAttribute("title", "Opret tilbud");
            request.setAttribute("offer", offerFacade.get(where(eq(ID, parameters.getInt("order")))));
            request.getRequestDispatcher("/WEB-INF/administration/create_offer.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Authentication authentication = new Authentication(request);
            Notifications  notifications  = notifications(request);
            Parameters     parameters     = new Parameters(request);

            if (!parameters.isInt("order") || !parameters.isInt("price")) {
                notifications.error("Incorrect form submit");
                response.sendRedirect("?action=create");
                return;
            }

            int order = parameters.getInt("order");
            int price = parameters.getInt("price");
            try {
                offerFacade.create(order, authentication.getEmployee().getId(), price);
                response.sendRedirect("offers");
            } catch (OfferValidatorException e) {
                for (OfferError error : e.getErrors())
                    notifications.error(errors.get(error));

                response.sendRedirect("?action=create");
            }
        }
    }
}
