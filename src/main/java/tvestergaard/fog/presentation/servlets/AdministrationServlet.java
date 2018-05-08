package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.servlets.commands.CommandDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

public class AdministrationServlet extends HttpServlet
{

    protected final CommandDispatcher dispatcher = new CommandDispatcher();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (!isAuthenticated(req, resp))
            return;

        req.setAttribute("context", "..");
        dispatcher.dispatch(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (!isAuthenticated(req, resp))
            return;

        req.setAttribute("context", "..");
        dispatcher.dispatch(req, resp);
    }

    private boolean isAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession   session       = req.getSession();
        Notifications notifications = notifications(req);
        if (session.getAttribute("employee") == null) {
            notifications.warning("Du skal være logget ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("authenticate");
            return false;
        }

        return true;
    }
}
