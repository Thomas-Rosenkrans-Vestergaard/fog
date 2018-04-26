package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.claddings.CladdingFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/administration/claddings")
public class AdministrationCladdings extends HttpServlet
{

    private final CladdingFacade claddingFacade = new CladdingFacade();
    private final CommandDispatcher dispatcher;

    public AdministrationCladdings(CommandDispatcher dispatcher)
    {
        this.dispatcher = new CommandDispatcher();
        this.dispatcher.register(new ShowCladdingsCommand());
    }

    /**
     * Shows the administration page for orders placed by claddings.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

    }

    private class ShowCladdingsCommand implements Command
    {

        /**
         * Checks whether or not the command accepts the provided request.
         *
         * @param request The request.
         * @return {@code true} when the commend accepts the request.
         */
        @Override
        public boolean accepts(HttpServletRequest request) throws ServletException, IOException
        {
            return true;
        }

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override
        public void dispatch(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException
        {
            try {
                request.setAttribute("title", "Tag");
                request.setAttribute("claddings", claddingFacade.get());
                request.getRequestDispatcher("/WEB-INF/administration/claddings.jsp").forward(request, response);
            } catch (ApplicationException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
