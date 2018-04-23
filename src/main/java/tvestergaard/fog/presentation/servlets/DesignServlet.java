package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingsDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingsDAO;
import tvestergaard.fog.data.flooring.FlooringsDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringsDAO;
import tvestergaard.fog.data.roofings.MysqlRoofingsDAO;
import tvestergaard.fog.data.roofings.RoofingsDAO;

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
     * The {@link RoofingsDAO} used when displaying possible roof choices to the customer.
     */
    private final RoofingsDAO roofingsDAO = new MysqlRoofingsDAO(ProductionDataSource.getSource());

    /**
     * The {@link FlooringsDAO} used when displaying possible floor choices to the customer.
     */
    private final FlooringsDAO flooringsDAO = new MysqlFlooringsDAO(ProductionDataSource.getSource());

    /**
     * The {@link CladdingsDAO} used when displaying possible cladding choices to the customer.
     */
    private final CladdingsDAO claddingsDAO = new MysqlCladdingsDAO(ProductionDataSource.getSource());

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
            req.setAttribute("roofings", roofingsDAO.getAll());
            req.setAttribute("floors", flooringsDAO.getAll());
            req.setAttribute("claddings", claddingsDAO.getAll());

            req.getRequestDispatcher("/WEB-INF/design.jsp").forward(req, resp);
        } catch (DataAccessException e) {
            throw new ServletException(e);
        }
    }
}
