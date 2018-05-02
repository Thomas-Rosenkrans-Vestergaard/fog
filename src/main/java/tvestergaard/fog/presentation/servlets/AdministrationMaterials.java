package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.logic.materials.MaterialError;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.materials.MaterialValidatorException;
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
import static tvestergaard.fog.data.materials.MaterialColumn.ID;
import static tvestergaard.fog.logic.materials.MaterialError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/materials")
public class AdministrationMaterials extends HttpServlet
{

    private final MaterialFacade             facade     = new MaterialFacade();
    private final CommandDispatcher          dispatcher = new CommandDispatcher();
    private final Map<MaterialError, String> errors     = new HashMap<>();

    public AdministrationMaterials()
    {
        dispatcher.get(null, new ShowTableCommand());
//        dispatcher.get("update", new ShowUpdateCommand());
//        dispatcher.post("update", new HandleUpdateCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.post("create", new HandleCreateCommand());

        errors.put(NUMBER_EMPTY, "Materialenummer er tomt.");
        errors.put(NUMBER_LONGER_THAN_12, "Materialet er længere end 12.");
        errors.put(DESCRIPTION_EMPTY, "Beskrivelsen må ikke være tom.");
        errors.put(DESCRIPTION_LONGER_THAN_255, "Beskrivelsen må ikke være længere end 255.");
        errors.put(HEIGHT_LESS_THAN_1, "Højden er mindre end 1.");
        errors.put(WIDTH_LESS_THAN_1, "Bredde er mindre end 1.");
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatcher.dispatch(req, resp);
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Materialee");
            request.setAttribute("materials", facade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_materials.jsp").forward(request, response);
        }
    }

    private class ShowCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Opret materiale");
            request.getRequestDispatcher("/WEB-INF/administration/create_material.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("number") ||
                    !parameters.isPresent("description") ||
                    !parameters.isPresent("notes") ||
                    !parameters.isInt("width") ||
                    !parameters.isInt("height") ||
                    !parameters.isInt("price")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("materials");
                return;
            }

            try {
                Material material = facade.create(
                        parameters.value("number"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getInt("unit"),
                        parameters.getInt("width"),
                        parameters.getInt("height"));

                notifications.success("Materialeet blev oprettet.");
                response.sendRedirect("?action=update&id=" + material.getId());

            } catch (MaterialValidatorException e) {
                for (MaterialError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
        }
    }
}
