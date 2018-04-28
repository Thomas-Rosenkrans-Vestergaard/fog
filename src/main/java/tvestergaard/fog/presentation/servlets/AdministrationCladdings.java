package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.logic.claddings.CladdingError;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.claddings.CladdingValidatorException;
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

import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.logic.claddings.CladdingError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/claddings")
public class AdministrationCladdings extends HttpServlet
{

    private final CladdingFacade             facade     = new CladdingFacade();
    private final CommandDispatcher          dispatcher = new CommandDispatcher();
    private final Map<CladdingError, String> errors     = new HashMap<>();

    public AdministrationCladdings()
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
     * Delegates dispatching of the incoming requests to the {@link CommandDispatcher}
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    /**
     * Delegates dispatching of the incoming requests to the {@link CommandDispatcher}
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }


    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Beklædninger");

            request.setAttribute("claddings", facade.get());
            request.getRequestDispatcher("/WEB-INF/administration/claddings.jsp").forward(request, response);
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
                response.sendRedirect("claddings");
                return;
            }

            Cladding cladding = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (cladding == null) {
                notifications.error("Unknown cladding.");
                response.sendRedirect("claddings");
                return;
            }

            request.setAttribute("title", "Opdater beklædning");
            request.setAttribute("cladding", cladding);
            request.getRequestDispatcher("/WEB-INF/administration/update_cladding.jsp").forward(request, response);
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
                response.sendRedirect("claddings");
                return;
            }

            try {
                facade.update(new CladdingRecord(
                        parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active")));

                notifications.success("Beklædningen blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (CladdingValidatorException e) {
                if (e.hasReason(EMPTY_NAME))
                    notifications.error("Det givne navn må ikke være tom.");
                if (e.hasReason(NAME_LONGER_THAN_255))
                    notifications.error("Det givne navn er for langt.");
                if (e.hasReason(EMPTY_DESCRIPTION))
                    notifications.error("Den givne beskrivelse må ikke være tom.");
                if (e.hasReason(NEGATIVE_PRICE))
                    notifications.error("Den givne pris må ikke være negativ.");
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
            request.setAttribute("title", "Opret beklædning");
            request.getRequestDispatcher("/WEB-INF/administration/create_cladding.jsp").forward(request, response);
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
                response.sendRedirect("claddings");
                return;
            }

            try {
                Cladding cladding = facade.create(parameters.value("name"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getBoolean("active"));

                notifications.success("Beklædningen blev oprettet.");
                response.sendRedirect("?action=update&id=" + cladding.getId());

            } catch (CladdingValidatorException e) {
                if (e.hasReason(EMPTY_NAME))
                    notifications.error(errors.get(EMPTY_NAME));
                if (e.hasReason(NAME_LONGER_THAN_255))
                    notifications.error(errors.get(NAME_LONGER_THAN_255));
                if (e.hasReason(EMPTY_DESCRIPTION))
                    notifications.error(errors.get(EMPTY_DESCRIPTION));
                if (e.hasReason(NEGATIVE_PRICE))
                    notifications.error(errors.get(NEGATIVE_PRICE));
                response.sendRedirect("?action=create");
            }
        }
    }
}
