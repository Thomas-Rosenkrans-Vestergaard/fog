package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.OfferColumn;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.logic.construction.ConstructionException;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.construction.GarageConstructionSummary;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InactiveEmployeeException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;
import tvestergaard.fog.logic.employees.UnknownEmployeeException;
import tvestergaard.fog.logic.offers.OfferError;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.offers.OfferValidatorException;
import tvestergaard.fog.logic.offers.OrderNotActiveException;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.orders.UnknownOrderException;
import tvestergaard.fog.presentation.*;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/administration/offers")
public class AdministrationOffersServlet extends AdministrationServlet
{

    private final OfferFacade             offerFacade = Facades.offerFacade;
    private final Map<OfferError, String> errors      = new HashMap();

    public AdministrationOffersServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.post("create", new HandleCreateCommand());

        errors.put(OfferError.NEGATIVE_PRICE, "Prisen må ikke være negativ.");
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.SALESMAN)) {
            notifications.error("Du skal være salgsmedarbejder for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_offers");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<OfferColumn> controls = new TableControls<>(request);
            controls.add(OfferColumn.CUSTOMER_NAME, TableControls.Type.TEXT);
            controls.add(OfferColumn.ORDER, TableControls.Type.INT);
            controls.add(OfferColumn.STATUS, TableControls.Type.TEXT);
            controls.add(OfferColumn.CREATED_AT, TableControls.Type.TIMESTAMP);
            controls.add(OfferColumn.EMPLOYEE_NAME, TableControls.Type.TEXT);
            controls.add(OfferColumn.PRICE, TableControls.Type.INT);

            notifications(request);
            request.setAttribute("title", "Tilbud");
            request.setAttribute("offers", offerFacade.get(controls.constraints()));
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/show_offers.jsp").forward(request, response);
        }
    }

    private class ShowCreateCommand implements Command
    {

        private final OrderFacade        orderFacade        = Facades.orderFacade;
        private final ConstructionFacade constructionFacade = Facades.constructionFacade;

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);
            if (!parameters.isInt("order")) {
                notifications.error("Illegal arguments.");
                response.sendRedirect("offers");
                return;
            }

            try {

                GarageConstructionSummary summary = constructionFacade.construct(parameters.getInt("order"));
                Order                     order   = orderFacade.get(parameters.getInt("order"));

                request.setAttribute("title", "Opret tilbud");
                request.setAttribute("order", order);
                request.setAttribute("summary", summary);
                request.setAttribute("csrf", csrf(request));
                request.getRequestDispatcher("/WEB-INF/administration/create_offer.jsp").forward(request, response);
                return;

            } catch (UnknownOrderException e) {
                notifications.error("No order with provided id.");
            } catch (ConstructionException e) {
                notifications.error(e.getClass().getCanonicalName()); // TODO: fix
            }

            response.sendRedirect("offers");
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Authentication authentication = new Authentication(request);
            Notifications  notifications  = notifications(request);
            Parameters     parameters     = new Parameters(request);

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=create");
                return;
            }

            if (!parameters.isInt("order") || !parameters.isFloat("price")) {
                notifications.error("Incorrect form submit");
                response.sendRedirect("?action=create");
                return;
            }

            int order = parameters.getInt("order");
            int price = Math.round(parameters.getFloat("price") * 100);

            try {
                offerFacade.create(order, authentication.getEmployee().getId(), price);
                notifications.success("Tilbudet blev oprettet.");
                response.sendRedirect("offers");
                return;
            } catch (OfferValidatorException e) {
                for (OfferError error : e.getErrors())
                    notifications.error(errors.get(error));
            } catch (UnknownOrderException e) {
                notifications.error("Ukendt order.");
            } catch (InactiveCustomerException e) {
                notifications.error("Inaktiv kunde.");
            } catch (UnknownEmployeeException e) {
                notifications.error("Ukendt kunde");
            } catch (InactiveEmployeeException e) {
                notifications.error("Ukendt medarbejder.");
            } catch (InsufficientPermissionsException e) {
                notifications.error("Medarbejderen er ikke en salgsperson.");
            } catch (OrderNotActiveException e) {
                notifications.error("Ordren er ikke aftiv, og kan derfor ikke modtage tilbud.");
            }

            response.sendRedirect("?action=create");
        }
    }
}
