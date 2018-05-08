package tvestergaard.fog.presentation.servlets.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command
{

    /**
     * Delegates the request and response objects to this command.
     *
     * @param request  The request.
     * @param response The response.
     */
    void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
