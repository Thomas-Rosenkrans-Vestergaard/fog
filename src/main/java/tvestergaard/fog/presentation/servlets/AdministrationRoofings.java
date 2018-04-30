package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.logic.roofings.RoofingError;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.logic.roofings.RoofingValidatorException;
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
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;
import static tvestergaard.fog.logic.roofings.RoofingError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/roofings")
public class AdministrationRoofings extends HttpServlet
{

    private final RoofingFacade             facade     = new RoofingFacade();
    private final CommandDispatcher         dispatcher = new CommandDispatcher();
    private final Map<RoofingError, String> errors     = new HashMap<>();

    public AdministrationRoofings()
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
        errors.put(MINIMUM_SLOPE_LESS_THAN_1, "Mindste hældning må ikke være mindre end 1.");
        errors.put(MINIMUM_SLOPE_GREATER_THAN_89, "Mindste hældning må ikke være større end 89.");
        errors.put(MAXIMUM_SLOPE_LESS_THAN_1, "Største hældning må ikke være mindre end 1.");
        errors.put(MAXIMUM_SLOPE_GREATER_THAN_89, "Største hældning må ikke være større end 89.");
        errors.put(MINIMUM_GREATER_THAN_MAXIMUM, "Mindste hældning større end mindte hældning.");
    }

    /**
     * Shows the administration page for orders placed by roofings.
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
     * Shows the administration page for orders placed by roofings.
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
            request.setAttribute("title", "Tage");
            request.setAttribute("roofings", facade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_roofings.jsp").forward(request, response);
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
                response.sendRedirect("roofings");
                return;
            }

            Roofing roofing = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (roofing == null) {
                notifications.error("Unknown roofing.");
                response.sendRedirect("roofings");
                return;
            }

            request.setAttribute("title", "Opdater tag");
            request.setAttribute("roofing", roofing);
            request.getRequestDispatcher("/WEB-INF/administration/update_roofing.jsp").forward(request, response);
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
                    !parameters.isInt("minimum-slope") ||
                    !parameters.isInt("maximum-slope") ||
                    !parameters.isInt("price") ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("roofings");
                return;
            }

            try {
                facade.update(
                        parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("minimum-slope"),
                        parameters.getInt("maximum-slope"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active"));

                notifications.success("Taget blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (RoofingValidatorException e) {
                for (RoofingError error : e.getErrors())
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
            request.setAttribute("title", "Opret tag");
            request.getRequestDispatcher("/WEB-INF/administration/create_roofing.jsp").forward(request, response);
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
                    !parameters.isInt("minimum-slope") ||
                    !parameters.isInt("maximum-slope") ||
                    !parameters.isInt("price") ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("roofings");
                return;
            }

            try {
                Roofing roofing = facade.create(parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("minimum-slope"),
                        parameters.getInt("maximum-slope"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active"));

                notifications.success("Taget blev oprettet.");
                response.sendRedirect("?action=update&id=" + roofing.getId());

            } catch (RoofingValidatorException e) {
                for (RoofingError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
        }
    }
}
