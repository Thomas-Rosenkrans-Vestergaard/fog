package tvestergaard.fog.presentation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher
{

    /**
     * The commands registered with the dispatcher.
     */
    private List<Command> commands = new ArrayList<>();

    /**
     * Registers the provided command with the dispatcher.
     *
     * @param command The command to register.
     */
    public void register(Command command)
    {
        this.commands.add(command);
    }

    /**
     * Dispatches the provided request and response to one of the registered commands.
     *
     * @param request  The request.
     * @param response The response.
     * @return {@code true} if the request and response was dispatched.
     */
    public boolean dispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        for (Command command : commands) {
            if (command.accepts(request)) {
                command.dispatch(request, response);
                return true;
            }
        }

        return false;
    }
}
