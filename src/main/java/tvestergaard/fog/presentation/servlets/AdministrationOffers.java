package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/offers")
public class AdministrationOffers extends HttpServlet
{

    private final OfferFacade offerFacade = Facades.offerFacade;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);
        if (!authentication.isEmployee()) {
            notifications.error("Du skal være logged ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("login");
            return;
        }

        req.setAttribute("title", "Tilbud");
        req.setAttribute("offers", offerFacade.get());
        req.getRequestDispatcher("/WEB-INF/administration/show_offers.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

    }
}
