package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.presentation.servlets.AdministrationServlet;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/administration", "/administration/index"})
public class AdministrationIndexServlet extends AdministrationServlet
{

    public AdministrationIndexServlet()
    {
        dispatcher.get(null, new IndexCommand());
    }

    private class IndexCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            request.setAttribute("employee", request.getSession().getAttribute("employee"));
            request.getRequestDispatcher("/WEB-INF/administration/administration_index.jsp").forward(request, response);
        }
    }
}
