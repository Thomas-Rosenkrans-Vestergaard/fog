package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;
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

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = {"/design", ""})
public class DesignServlet extends HttpServlet
{

    /**
     * The {@link RoofingDAO} used when displaying possible roof choices to the customer.
     */
    private final RoofingDAO roofingsDAO = new MysqlRoofingDAO(ProductionDataSource.getSource());

    /**
     * The {@link FlooringDAO} used when displaying possible floor choices to the customer.
     */
    private final FlooringDAO flooringsDAO = new MysqlFlooringDAO(ProductionDataSource.getSource());

    /**
     * The {@link CladdingDAO} used when displaying possible cladding choices to the customer.
     */
    private final CladdingDAO claddingsDAO = new MysqlCladdingDAO(ProductionDataSource.getSource());

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
            req.setAttribute("roofings", roofingsDAO.get());
            req.setAttribute("floors", flooringsDAO.get());
            req.setAttribute("claddings", claddingsDAO.get());

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
        Parameters    parameters    = new Parameters(req);

        if (parametersNotValid(parameters)) {
            notifications.error("The provided data is invalid.");
            return;
        }


    }

    /**
     * Validates that the provided parameters can safely be converted to their target type.
     *
     * @param parameters
     * @return
     */
    private boolean parametersNotValid(Parameters parameters)
    {
        return parameters.isEnum("", EnumSet.allOf(Order.Type.class)) &&
               parameters.isInt("cladding") &&
               parameters.isInt("width") &&
               parameters.isInt("length") &&
               parameters.isInt("height") &&
               parameters.isInt("roofing") &&
               parameters.isInt("slope") &&
               parameters.isEnum("rafters", EnumSet.allOf(Order.Rafters.class)) &&
               (!parameters.isPresent("shed") || (
                       parameters.isInt("shed-width") &&
                       parameters.isInt("shed-depth") &&
                       (!parameters.isPresent("flooring") || parameters.isInt("flooring")) &&
                       parameters.isInt("shed-cladding"))) &&
               parameters.isPresent("name") &&
               parameters.isPresent("address") &&
               parameters.isPresent("email") &&
               parameters.isEnum("contact-method", EnumSet.allOf(Customer.ContactMethod.class));
    }
}
