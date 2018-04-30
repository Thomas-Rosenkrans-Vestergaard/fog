package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.logic.floorings.FlooringError;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.floorings.FlooringValidatorException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.flooring.FlooringColumn.ID;
import static tvestergaard.fog.logic.floorings.FlooringError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/floorings")
public class AdministrationFlooring extends HttpServlet
{

    private final FlooringFacade             facade     = new FlooringFacade();
    private final CommandDispatcher          dispatcher = new CommandDispatcher();
    private final Map<FlooringError, String> errors     = new HashMap<>();

    public AdministrationFlooring()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("create", new HandleCreateCommand());
        dispatcher.post("update", new HandleUpdateCommand());

        errors.put(EMPTY_NAME, "Det givne navn må ikke være tom.");
        errors.put(NAME_LONGER_THAN_255, "Det givne navn er for langt.");
        errors.put(EMPTY_DESCRIPTION, "Den givne beskrivelse må ikke være tom.");
        errors.put(NEGATIVE_PRICE, "Den givne pris må ikke være negativ.");
    }

    /**
     * Shows the administration page for orders placed by floorings.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    /**
     * Shows the administration page for orders placed by floorings.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Gulve");
            request.setAttribute("floorings", facade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_floorings.jsp").forward(request, response);
        }
    }

    class ShowUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id")) {
                notifications.error("No identifier provided.");
                response.sendRedirect("floorings");
                return;
            }

            Flooring flooring = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (flooring == null) {
                notifications.error("Unknown flooring.");
                response.sendRedirect("floorings");
                return;
            }

            request.setAttribute("title", "Opdater gulv");
            request.setAttribute("flooring", flooring);
            request.getRequestDispatcher("/WEB-INF/administration/update_flooring.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id") ||
                    !parameters.isPresent("name") ||
                    !parameters.isPresent("description") ||
                    !parameters.isInt("price") ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("floorings");
                return;
            }

            try {
                facade.update(
                        parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active"));

                notifications.success("Gulvet blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (FlooringValidatorException e) {
                for (FlooringError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
                return;
            }
        }
    }

    private class ShowCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Opret gulv");
            request.getRequestDispatcher("/WEB-INF/administration/create_flooring.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("name") ||
                    !parameters.isPresent("description") ||
                    !parameters.isInt("price") ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("floorings");
                return;
            }

            try {
                Flooring flooring = facade.create(parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active"));

                notifications.success("Gulvet blev oprettet.");
                response.sendRedirect("?action=update&id=" + flooring.getId());

            } catch (FlooringValidatorException e) {
                for (FlooringError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
        }
    }
}
