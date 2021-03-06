package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.logic.employees.EmployeeFacade;
import tvestergaard.fog.logic.employees.InactiveEmployeeException;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/administration/authenticate")
public class AdministrationAuthenticationServlet extends AdministrationServlet
{

    private final EmployeeFacade facade = Facades.employeeFacade;

    /**
     * Shows a page where employees can authenticate themselves.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("title", "Medarbejderlogin");
        req.setAttribute("context", "..");
        req.setAttribute("csrf", csrf(req));
        req.getRequestDispatcher("/WEB-INF/administration/authentication.jsp").forward(req, resp);
    }

    /**
     * Handles the information sent by the login form on the /administration/login page.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!parameters.isPresent("username") || !parameters.isPresent("password")) {
            notifications.error("Not enough information.");
            resp.sendRedirect("authenticate");
            return;
        }

        if (!verify(req)) {
            notifications.error("Token udløbet.");
            resp.sendRedirect("authenticate");
            return;
        }

        try {
            Employee employee = facade.authenticate(parameters.value("username"), parameters.value("password"));
            if (employee == null) {
                notifications.error("Forkert brugernavn eller adgangskode.");
                resp.sendRedirect("authenticate");
                return;
            }

            String      from    = req.getParameter("from");
            HttpSession session = req.getSession();
            session.setAttribute("employee", employee);
            resp.sendRedirect(from != null ? URLDecoder.decode(from, "UTF-8") : "index");

        } catch (InactiveEmployeeException e) {
            notifications.error("Inaktiv medarbejder.");
            resp.sendRedirect("models");
        }
    }
}
