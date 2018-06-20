package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderColumn;
import tvestergaard.fog.data.orders.ShedRecord;
import tvestergaard.fog.data.orders.ShedUpdater;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.*;
import tvestergaard.fog.logic.roofings.RoofingFacade;
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
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.OrderDirection.ASC;
import static tvestergaard.fog.data.constraints.OrderDirection.DESC;
import static tvestergaard.fog.data.orders.OrderColumn.*;
import static tvestergaard.fog.logic.orders.OrderError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(name = "AdministrationOrders", urlPatterns = "/administration/orders")
public class AdministrationOrdersServlet extends AdministrationServlet
{

    private final OrderFacade             orderFacade    = Facades.orderFacade;
    private final CladdingFacade          claddingFacade = Facades.claddingFacade;
    private final FlooringFacade          flooringFacade = Facades.flooringFacade;
    private final RoofingFacade           roofingFacade  = Facades.roofingFacade;
    private final OfferFacade             offerFacade    = Facades.offerFacade;
    private final Map<OrderError, String> errors         = new HashMap<>();

    public AdministrationOrdersServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("update", new HandleUpdateCommand());
        dispatcher.post("cancel", new HandleCancelCommand());

        errors.put(INCOMPLETE_ORDER, "Incomplete order.");
        errors.put(UNKNOWN_CUSTOMER, "Ukendt kunde");
        errors.put(INACTIVE_CUSTOMER, "Inaktive kunde.");
        errors.put(UNKNOWN_CLADDING, "Ukendt beklædning.");
        errors.put(INACTIVE_CLADDING, "Inaktive beklædning.");
        errors.put(ILLEGAL_WIDTH, "Ulovlig bredde.");
        errors.put(ILLEGAL_LENGTH, "Ulovlig længde.");
        errors.put(ILLEGAL_HEIGHT, "Ulovlig højde.");
        errors.put(UNKNOWN_ROOFING, "Ukendt tag.");
        errors.put(INACTIVE_ROOFING, "Inaktiv tag.");
        errors.put(ILLEGAL_SLOPE, "Ulovlig hældning.");
        errors.put(ILLEGAL_SHED_DEPTH, "Ulovlig skurhøjde.");
        errors.put(UNKNOWN_SHED_CLADDING, "Ukendt skurbeklædning.");
        errors.put(INACTIVE_SHED_CLADDING, "Inaktiv skurbeklædning.");
        errors.put(UNKNOWN_SHED_FLOORING, "Ukendt skurordre");
        errors.put(INACTIVE_SHED_FLOORING, "Inaktiv skurordre.");
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Constraints<OrderColumn> defaultConstraints = new Constraints<>();
            defaultConstraints.order(OrderColumn.ACTIVE, DESC);
            defaultConstraints.order(OrderColumn.OPEN_OFFERS, ASC);
            TableControls<OrderColumn> controls = new TableControls<>(request, defaultConstraints);
            controls.add(CUSTOMER_NAME, TableControls.Type.TEXT);
            controls.add(WIDTH, TableControls.Type.INT);
            controls.add(LENGTH, TableControls.Type.INT);
            controls.add(HEIGHT, TableControls.Type.INT);
            controls.add(SLOPE, TableControls.Type.INT);
            controls.add(CREATED_AT, TableControls.Type.TIMESTAMP);
            notifications(request);
            request.setAttribute("title", "Ordre");
            request.setAttribute("orders", orderFacade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_orders.jsp").forward(request, response);
        }
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.SALESMAN)) {
            notifications.error("Du skal være salgsmedarbejder for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_orders");
        return true;
    }

    class ShowUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id")) {
                notifications.error("No identifier provided.");
                response.sendRedirect("orders");
                return;
            }

            Order order = orderFacade.get(parameters.getInt("id"));
            if (order == null) {
                notifications.error("Unknown order.");
                response.sendRedirect("orders");
                return;
            }

            request.setAttribute("title", "Opdater ordre");
            request.setAttribute("order", order);
            request.setAttribute("claddings", claddingFacade.get());
            request.setAttribute("roofings", roofingFacade.get());
            request.setAttribute("floorings", flooringFacade.get());
            request.setAttribute("offers", offerFacade.getByOrder(order.getId()));
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/update_order.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("width") ||
                    !parameters.isInt("length") ||
                    !parameters.isInt("height") ||
                    !parameters.isInt("roofing") ||
                    !parameters.isInt("slope") ||
                    !parameters.isBoolean("active") ||
                    !parameters.isPresent("shed-action")) {
                notifications.error("The provided data is invalid.");
                response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                return;
            }

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
                return;
            }

            if (parameters.isPresent("shed")) {
                if ("create".equals(parameters.value("shed-action"))) {
                    if (!parameters.isInt("shed-depth") ||
                            !parameters.isPresent("shed-flooring") ||
                            !parameters.isInt("shed-cladding")) {
                        notifications.error("Manglende inforamtion om redskabsskur.");
                        response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                        return;
                    }
                } else {
                    if (!parameters.isInt("shed-id") ||
                            !parameters.isInt("shed-depth") ||
                            !parameters.isPresent("shed-flooring") ||
                            !parameters.isInt("shed-cladding")) {
                        notifications.error("The provided data is invalid.");
                        response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                        return;
                    }
                }
            }

            try {
                orderFacade.update(
                        parameters.getInt("id"),
                        parameters.getInt("width"),
                        parameters.getInt("length"),
                        parameters.getInt("height"),
                        parameters.getInt("roofing"),
                        parameters.getInt("slope"),
                        parameters.getBoolean("active"),
                        createShed(parameters),
                        parameters.value("comment"));

                notifications.success("Ordren blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (OrderValidatorException e) {
                for (OrderError error : e.getReasons())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
                return;
            }
        }
    }

    private ShedUpdater createShed(Parameters parameters)
    {
        if (!parameters.isPresent("shed"))
            return null;


        return new ShedRecord(
                "update".equals(parameters.value("shed-action")) ? parameters.getInt("shed-id") : -1,
                parameters.getInt("shed-depth"),
                parameters.getInt("shed-cladding"),
                null,
                parameters.getInt("shed-flooring"),
                null);
    }

    private class HandleCancelCommand implements Command
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

            if (!parameters.isInt("id")) {
                notifications.error("No provided id.");
                response.sendRedirect("orders");
                return;
            }

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                return;
            }

            try {
                orderFacade.cancel(parameters.getInt("id"), true);
                notifications.success("Ordren blev aflyst.");
                response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                return;
            } catch (UnknownOrderException e) {
                notifications.error("Ukendt order");
            } catch (InactiveOrderException e) {
                notifications.error("Denne ordre er allerede aflyst eller købt.");
            }

            response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
        }
    }
}
