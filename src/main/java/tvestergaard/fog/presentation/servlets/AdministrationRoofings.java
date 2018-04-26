package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.RoofingFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/administration/roofings")
public class AdministrationRoofings extends HttpServlet
{

    private final RoofingFacade roofingFacade = new RoofingFacade();

    /**
     * Shows the administration page for orders placed by roofings.
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
            req.setAttribute("title", "Tag");
            req.setAttribute("roofings", roofingFacade.get());
            req.getRequestDispatcher("/WEB-INF/administration/roofings.jsp").forward(req, resp);
        } catch (ApplicationException e) {
            throw new IllegalStateException(e);
        }
    }
}
