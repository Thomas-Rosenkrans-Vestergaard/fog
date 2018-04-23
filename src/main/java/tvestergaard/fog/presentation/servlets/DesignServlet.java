package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.roofings.MysqlRoofingDAO;
import tvestergaard.fog.data.roofings.RoofingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
