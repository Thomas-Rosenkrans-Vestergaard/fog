package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderColumn;
import tvestergaard.fog.data.orders.RafterChoice;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.OrderError;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.orders.OrderValidatorException;
import tvestergaard.fog.logic.orders.ShedSpecification;
import tvestergaard.fog.logic.roofings.RoofingFacade;
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
import static tvestergaard.fog.data.orders.OrderColumn.ID;
import static tvestergaard.fog.logic.orders.OrderError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

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
            TableControls<OrderColumn> controls = new TableControls<>(request, OrderColumn.class, orderFacade.size());
            notifications(request);
            request.setAttribute("title", "Ordree");
            request.setAttribute("orders", orderFacade.get(controls.constraints()));
            request.getRequestDispatcher("/WEB-INF/administration/show_orders.jsp").forward(request, response);
        }
    }

    @Override protected void before(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("navigation", "administration_orders");
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

            Order order = orderFacade.first(where(eq(ID, parameters.getInt("id"))));
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
            request.setAttribute("offers", offerFacade.get(order.getId()));
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
                    !parameters.isEnum("rafters", RafterChoice.class) ||
                    !parameters.isInt("shed-depth") ||
                    !parameters.isPresent("shed-flooring") ||
                    !parameters.isInt("shed-cladding")) {
                notifications.error("The provided data is invalid.");
                response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
                return;
            }

            try {
                orderFacade.update(
                        parameters.getInt("id"),
                        parameters.getInt("width"),
                        parameters.getInt("length"),
                        parameters.getInt("height"),
                        parameters.getInt("roofing"),
                        parameters.getInt("slope"),
                        parameters.getEnum("rafters", RafterChoice.class),
                        createShed(parameters));

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

    private ShedSpecification createShed(Parameters parameters)
    {
        if (!parameters.isPresent("shed"))
            return null;

        return new ShedSpecification(
                parameters.getInt("shed-depth"),
                parameters.getInt("shed-cladding"),
                parameters.getInt("shed-flooring")
        );
    }
}
