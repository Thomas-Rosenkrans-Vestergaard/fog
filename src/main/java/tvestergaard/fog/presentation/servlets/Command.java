package tvestergaard.fog.presentation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command
{

    /**
     * Checks whether or not the command accepts the provided request.
     *
     * @param request The request.
     * @return {@code true} when the commend accepts the request.
     */
    boolean accepts(HttpServletRequest request) throws ServletException, IOException;

    /**
     * Delegates the request and response objects to this command.
     *
     * @param request  The request.
     * @param response The response.
     */
    void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
