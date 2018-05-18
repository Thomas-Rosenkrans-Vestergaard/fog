package tvestergaard.fog.presentation.servlets;

import org.pmw.tinylog.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(urlPatterns = "/error-handler")
public class ErrorHandler extends HttpServlet
{
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");
        Logger.error(exception, "Exception from servlet.");

        Writer writer = resp.getWriter();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        writer.append("<p>Der skete en uhånterbar fejl.</p>");
        writer.flush();
        writer.close();
    }
}
