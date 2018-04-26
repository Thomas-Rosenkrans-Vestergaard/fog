package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.customers.ContactMethod;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.flooring.FlooringColumn;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.RafterChoice;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.data.sheds.ShedSpecification;
import tvestergaard.fog.logic.customers.CustomerError;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.customers.CustomerValidatorException;
import tvestergaard.fog.logic.OrderFacade;
import tvestergaard.fog.logic.OrderValidationException;
import tvestergaard.fog.presentation.FormResponse;
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
import java.util.EnumSet;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.logic.customers.CustomerError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.formResponse;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = {"/design", ""})
public class DesignServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = new CustomerFacade();
    private final RoofingDAO     roofingsDAO    = new MysqlRoofingDAO(ProductionDataSource.getSource());
    private final FlooringDAO    flooringsDAO   = new MysqlFlooringDAO(ProductionDataSource.getSource());
    private final CladdingDAO    claddingsDAO   = new MysqlCladdingDAO(ProductionDataSource.getSource());
    private final OrderFacade    orderFacade    = new OrderFacade();

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
        try {
            req.setAttribute("roofings", roofingsDAO.get(where(eq(RoofingColumn.ACTIVE, true))));
            req.setAttribute("floorings", flooringsDAO.get(where(eq(FlooringColumn.ACTIVE, true))));
            req.setAttribute("claddings", claddingsDAO.get(where(eq(CladdingColumn.ACTIVE, true))));

            req.getRequestDispatcher("/WEB-INF/design.jsp").forward(req, resp);
        } catch (DataAccessException e) {
            throw new ServletException(e);
        }
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
        Notifications notifications = notifications(req);
        Parameters parameters = new Parameters(req);

        if (!validateParameters(parameters)) {
            notifications.error("The provided data is invalid.");
            req.getRequestDispatcher("/WEB-INF/design.jsp").forward(req, resp);
            return;
        }

        try {

            Customer customer = customerFacade.create(
                    parameters.value("customer-name"),
                    parameters.value("customer-address"),
                    parameters.value("customer-email"),
                    parameters.value("customer-phone"),
                    parameters.getEnum("contact-method", EnumSet.allOf(ContactMethod.class)),
                    null,
                    true);

            Order order = orderFacade.create(
                    customer.getId(),
                    parameters.getInt("cladding"),
                    parameters.getInt("width"),
                    parameters.getInt("length"),
                    parameters.getInt("height"),
                    parameters.getInt("roofing"),
                    parameters.getInt("slope"),
                    parameters.getEnum("rafters", EnumSet.allOf(RafterChoice.class)),
                    createShed(parameters));

            notifications.success("Din ordre blev registreret.");
            resp.sendRedirect("administration/orders");

        } catch (CustomerValidatorException e) {
//            FormResponse response = formResponse(req);
//            populateFormResponse(response, parameters, e);
            for (CustomerError reason : e.getReasons())
                notifications.error(reason.name());
            resp.sendRedirect("design");
        } catch (OrderValidationException e) {
            FormResponse response = formResponse(req);
            populateFormResponse(response, parameters, e);
            resp.sendRedirect("design");
        }
    }

    private void populateFormResponse(FormResponse formResponse, Parameters parameters, OrderValidationException e)
    {

    }

    private void populateFormResponse(FormResponse formResponse, Parameters parameters, CustomerValidatorException e)
    {
        if (e.isReason(NAME_EMPTY))
            formResponse.addError("name", "Navnet må ikke være tomt.");
        if (e.isReason(NAME_LONGER_THAN_255))
            formResponse.addError("name", "Navnet må ikke være så langt.");
        if (e.isReason(ADDRESS_EMPTY))
            formResponse.addError("address", "Addressen må ikke være tomt.");
        if (e.isReason(ADDRESS_LONGER_THAN_255))
            formResponse.addError("address", "Addressen må ikke være så langt.");
        if (e.isReason(EMAIL_INVALID))
            formResponse.addError("email", "Email addressen er ikke valid.");
        if (e.isReason(EMAIL_LONGER_THAN_255))
            formResponse.addError("address", "Emailadddressen må ikke være så langt.");
        if (e.isReason(EMAIL_TAKEN))
            formResponse.addError("email", "Den givne email er allerde i brug på siden.");
        if (e.isReason(PHONE_EMPTY))
            formResponse.addError("phone", "Telefonnumeret må ikke være tomt.");
        if (e.isReason(PHONE_LONGER_THAN_30))
            formResponse.addError("phone", "Telefonnumeret må ikke være så langt.");
        if (e.isReason(PASSWORD_SHORTER_THAN_4))
            formResponse.addError("password", "Adgangskoden skal være længere end 3.");
        if (e.isReason(UNKNOWN_CONTACT_METHOD))
            formResponse.addError("contact-method", "Ukendt kontaktmetode.");
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
                parameters.isEnum("rafters", EnumSet.allOf(RafterChoice.class)) &&
                (!parameters.isPresent("shed") || (
                        parameters.isInt("shed-width") &&
                                parameters.isInt("shed-depth") &&
                                (!parameters.isPresent("shed-flooring") || parameters.isInt("shed-flooring")) &&
                                parameters.isInt("shed-cladding"))) &&
                parameters.isPresent("customer-name") &&
                parameters.isPresent("customer-address") &&
                parameters.isPresent("customer-email") &&
                parameters.isPresent("customer-phone") &&
                parameters.isEnum("contact-method", EnumSet.allOf(ContactMethod.class));
    }
}
