package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.flooring.FlooringColumn;
import tvestergaard.fog.data.orders.RafterChoice;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.orders.*;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = {"/place-order", ""})
public class PlaceOrderServlet extends HttpServlet
{

    private final CustomerFacade customerFacade  = Facades.customerFacade;
    private final RoofingFacade  roofingsFacade  = Facades.roofingFacade;
    private final FlooringFacade flooringsFacade = Facades.flooringFacade;
    private final CladdingFacade claddingsFacade = Facades.claddingFacade;
    private final OrderFacade    orderFacade     = Facades.orderFacade;

    /**
     * Displays the /design page, where customers can design their own garage.
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

        req.setAttribute("title", "Placér ordre");
        req.setAttribute("navigation", "place-order");
        req.setAttribute("roofings", roofingsFacade.get(where(eq(RoofingColumn.ACTIVE, true))));
        req.setAttribute("floorings", flooringsFacade.get(where(eq(FlooringColumn.ACTIVE, true))));
        req.setAttribute("claddings", claddingsFacade.get(where(eq(CladdingColumn.ACTIVE, true))));
        req.setAttribute("customer", authentication.getCustomer());
        req.getRequestDispatcher("/WEB-INF/place_order.jsp").forward(req, resp);
    }

    /**
     * Accepts the data posted from /design.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends  to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request for the POST could not be handled
     * @see ServletOutputStream
     * @see ServletResponse#setContentType
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Notifications  notifications  = notifications(req);
        Parameters     parameters     = new Parameters(req);
        Authentication authentication = new Authentication(req);

        if (!parameters.isInt("cladding") ||
                !parameters.isInt("width") ||
                !parameters.isInt("length") ||
                !parameters.isInt("height") ||
                !parameters.isInt("roofing") ||
                !parameters.isInt("slope") ||
                !parameters.isEnum("rafters", RafterChoice.class) /*||
                (!parameters.isPresent("shed") || (
                        parameters.isInt("shed-width") ||
                                parameters.isInt("shed-depth") ||
                                (!parameters.isPresent("shed-flooring") || parameters.isInt("shed-flooring")) ||
                                parameters.isInt("shed-cladding")))*/) {
            notifications.error("Invalid design data.");
            resp.sendRedirect("design");
            return;
        }

        if (!authentication.isCustomer()) {
            if (!parameters.isPresent("customer-name") ||
                    !parameters.isPresent("customer-address") ||
                    !parameters.isPresent("customer-email") ||
                    !parameters.isPresent("customer-phone")) {
                notifications.error("Invalid customer data.");
                resp.sendRedirect("design");
                return;
            }
        }

        try {

            Customer customer = authentication.isCustomer() ? authentication.getCustomer() :
                                customerFacade.authenticate(parameters.value("customer-email"), parameters.value("customer.password"));

            if (customer == null) {
                notifications.error("Incorrect customer credentials.");
                resp.sendRedirect("design");
                return;
            }

            orderFacade.create(
                    customer.getId(),
                    parameters.getInt("cladding"),
                    parameters.getInt("width"),
                    parameters.getInt("length"),
                    parameters.getInt("height"),
                    parameters.getInt("roofing"),
                    parameters.getInt("slope"),
                    parameters.getEnum("rafters", RafterChoice.class),
                    null /*createShed(parameters)*/);

            notifications.success("Din ordre blev registreret.");
            resp.sendRedirect("orders");
            return;

        } catch (OrderValidatorException e) {
            for (OrderError error : e.getReasons())
                notifications.error(error.name());
        } catch (UnconfirmedCustomerException e) {
            notifications.error("Du har endnu ikke bekræftet din mailaddresse.");
        } catch (InactiveCustomerException e) {
            notifications.error("Du kan ikke placére en ordre, sides du er makeret inaktiv.");
        }

        resp.sendRedirect("design");
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
}
