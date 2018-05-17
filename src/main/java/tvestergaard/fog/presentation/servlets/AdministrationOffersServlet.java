package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.OfferColumn;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderColumn;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.construction.GarageConstructionSummary;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InactiveEmployeeException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;
import tvestergaard.fog.logic.employees.UnknownEmployeeException;
import tvestergaard.fog.logic.offers.OfferError;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.offers.OfferValidatorException;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.orders.UnknownOrderException;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

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
        @Override
        public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<OfferColumn> controls = new TableControls<>(request, OfferColumn.class, OfferColumn.SEARCH);
            notifications(request);
            request.setAttribute("title", "Tilbud");
            request.setAttribute("offers", offerFacade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_offers.jsp").forward(request, response);
        }
    }

    private class ShowCreateCommand implements Command
    {

        private final OrderFacade        orderFacade        = Facades.orderFacade;
        private final ConstructionFacade constructionFacade = Facades.constructionFacade;

        @Override
        public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);
            if (!parameters.isInt("order")) {
                notifications.error("Illegal arguments.");
                response.sendRedirect("offers");
                return;
            }

            Order order = orderFacade.first(where(eq(OrderColumn.ID, parameters.getInt("order"))));

            if (order == null) {
                notifications.error("No order with provided id.");
                response.sendRedirect("offers");
                return;
            }

            GarageConstructionSummary summary = constructionFacade.construct(order);

            request.setAttribute("title", "Opret tilbud");
            request.setAttribute("order", order);
            request.setAttribute("summary", summary);
            request.getRequestDispatcher("/WEB-INF/administration/create_offer.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override
        public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
            }

            response.sendRedirect("?action=create");
        }
    }
}
