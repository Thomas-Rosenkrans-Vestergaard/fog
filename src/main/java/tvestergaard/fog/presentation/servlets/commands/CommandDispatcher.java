package tvestergaard.fog.presentation.servlets.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandDispatcher
{


    /**
     * The commands registered with the dispatcher.
     */
    private Map<Integer, Command> commands = new HashMap<>();

    /**
     * Registers the provided command with the dispatcher.
     *
     * @param command The command to register.
     */
    public void get(String action, Command command)
    {
        this.commands.put(Objects.hash(HttpMethod.GET, action), command);
    }

    public void post(String action, Command command)
    {
        this.commands.put(Objects.hash(HttpMethod.POST, action), command);
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
        Command command = commands.get(Objects.hash(HttpMethod.valueOf(request.getMethod()), request.getParameter
                ("action")));

        if (command != null)
            command.dispatch(request, response);

        return false;
    }
}
