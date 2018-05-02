package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.RafterChoice;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.orders.OrderError;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.orders.OrderValidatorException;
import tvestergaard.fog.logic.orders.ShedSpecification;
import tvestergaard.fog.logic.roofings.RoofingFacade;
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
import static tvestergaard.fog.data.orders.OrderColumn.ID;
import static tvestergaard.fog.logic.orders.OrderError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(name = "AdministrationOrders", urlPatterns = "/administration/orders")
public class AdministrationOrders extends HttpServlet
{

    private final OrderFacade             orderFacade    = Facades.orderFacade;
    private final CladdingFacade          claddingFacade = Facades.claddingFacade;
    private final FlooringFacade          flooringFacade = Facades.flooringFacade;
    private final RoofingFacade           roofingFacade  = Facades.roofingFacade;
    private final CommandDispatcher       dispatcher     = new CommandDispatcher();
    private final Map<OrderError, String> errors         = new HashMap<>();

    public AdministrationOrders()
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
        errors.put(ILLEGAL_SHED_WIDTH, "Ulovlig skurbredde.");
        errors.put(ILLEGAL_SHED_DEPTH, "Ulovlig skurhøjde.");
        errors.put(UNKNOWN_SHED_CLADDING, "Ukendt skurbeklædning.");
        errors.put(INACTIVE_SHED_CLADDING, "Inaktiv skurbeklædning.");
        errors.put(UNKNOWN_SHED_FLOORING, "Ukendt skurordre");
        errors.put(INACTIVE_SHED_FLOORING, "Inaktiv skurordre.");
    }

    /**
     * Shows the administration page for orders placed by orders.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

    /**
     * Shows the administration page for orders placed by orders.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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
            notifications(request);
            request.setAttribute("title", "Ordree");
            request.setAttribute("orders", orderFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_orders.jsp").forward(request, response);
        }
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
            request.getRequestDispatcher("/WEB-INF/administration/update_order.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!validateParameters(parameters)) {
                notifications.error("The provided data is invalid.");
                request.getRequestDispatcher("/WEB-INF/design.jsp").forward(request, response);
                return;
            }

            try {
                orderFacade.update(
                        parameters.getInt("id"),
                        parameters.getInt("cladding"),
                        parameters.getInt("width"),
                        parameters.getInt("length"),
                        parameters.getInt("height"),
                        parameters.getInt("roofing"),
                        parameters.getInt("slope"),
                        parameters.getEnum("rafters", RafterChoice.class),
                        createShed(parameters));

                notifications.success("Ordreet blev opdateret.");
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
        if (!parameters.isPresent("shed")) {
            return null;
        }

        return new ShedSpecification(
                parameters.getInt("shed-width"),
                parameters.getInt("shed-depth"),
                parameters.getInt("shed-cladding"),
                parameters.getInt("shed-flooring")
        );
    }

    /**
     * Validates that the provided parameters can safely be converted to their target type.
     *
     * @param parameters
     * @return
     */
    private boolean validateParameters(Parameters parameters)
    {
        return parameters.isInt("cladding") &&
                parameters.isInt("width") &&
                parameters.isInt("length") &&
                parameters.isInt("height") &&
                parameters.isInt("roofing") &&
                parameters.isInt("slope") &&
                parameters.isEnum("rafters", RafterChoice.class) &&
                (!parameters.isPresent("shed") || (
                        parameters.isInt("shed-width") &&
                                parameters.isInt("shed-depth") &&
                                (!parameters.isPresent("shed-flooring") || parameters.isInt("shed-flooring")) &&
                                parameters.isInt("shed-cladding"))) &&
                parameters.isPresent("customer-name") &&
                parameters.isPresent("customer-address") &&
                parameters.isPresent("customer-email") &&
                parameters.isPresent("customer-phone");
    }
}
