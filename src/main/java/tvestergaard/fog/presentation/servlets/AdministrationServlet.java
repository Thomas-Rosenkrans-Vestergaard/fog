package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.servlets.commands.CommandDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

public abstract class AdministrationServlet extends HttpServlet
{

    protected final CommandDispatcher dispatcher = new CommandDispatcher();

    protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        return true;
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (!isAuthenticated(req, resp))
            return;

        req.setAttribute("context", "..");
        if (!before(req, resp, (Employee) req.getSession().getAttribute("employee")))
            return;
        dispatcher.dispatch(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (!isAuthenticated(req, resp))
            return;

        req.setAttribute("context", "..");
        if (!before(req, resp, (Employee) req.getSession().getAttribute("employee")))
            return;
        dispatcher.dispatch(req, resp);
    }

    private boolean isAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession   session       = req.getSession();
        Notifications notifications = notifications(req);
        if (session.getAttribute("employee") == null) {
            notifications.warning("Du skal være logget ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("authenticate?from=" + URLEncoder.encode(getFromUrl(req), "UTF-8"));
            return false;
        }

        return true;
    }

    private String getFromUrl(HttpServletRequest req)
    {
        String servletPath = req.getServletPath();
        String relative    = servletPath.replaceAll("/administration/", "");
        String queryString = req.getQueryString();

        if (queryString == null)
            return relative;

        return relative + '?' + queryString;
    }
}
