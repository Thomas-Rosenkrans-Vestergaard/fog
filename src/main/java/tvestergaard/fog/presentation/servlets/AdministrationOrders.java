package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.orders.MysqlOrderDAO;
import tvestergaard.fog.data.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdministrationOrders", urlPatterns = "/administration/orders")
public class AdministrationOrders extends HttpServlet
{

    /**
     * The {@link OrderDAO} used when displaying possible roof choices to the customer.
     */
    private final OrderDAO orderDAO = new MysqlOrderDAO(ProductionDataSource.getSource());

    /**
     * Shows the administration page for orders placed by customers.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     * @see ServletResponse#setContentType
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try {
            req.setAttribute("title", "Ordre");
            req.setAttribute("orders", orderDAO.get());
            req.getRequestDispatcher("/WEB-INF/administration/orders.jsp").forward(req, resp);
        } catch (DataAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
