package tvestergaard.fog.presentation.servlets;

import org.pmw.tinylog.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet(urlPatterns = "/error-handler")
public class ErrorHandler extends HttpServlet
{
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");
        Logger.error(exception, "Exception from servlet.");

        Writer      writer      = resp.getWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        resp.setContentType("text");
        resp.setCharacterEncoding("UTF-8");
//        writer.write("<pre>");
//        exception.printStackTrace(printWriter);
//        writer.write("</pre>");
        writer.write("Der skete en fejl.");
        writer.flush();
        writer.close();
    }
}
