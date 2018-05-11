package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentReference;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingType;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.roofings.RoofingError;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.logic.roofings.RoofingValidatorException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;
import static tvestergaard.fog.logic.roofings.RoofingError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/roofings")
public class AdministrationRoofingsServlet extends AdministrationServlet
{

    private final RoofingFacade             roofingFacade  = Facades.roofingFacade;
    private final MaterialFacade            materialFacade = Facades.materialFacade;
    private final Map<RoofingError, String> errors         = new HashMap<>();

    public AdministrationRoofingsServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("create", new HandleCreateCommand());
        dispatcher.post("update", new HandleUpdateCommand());

        errors.put(EMPTY_NAME, "Det givne navn må ikke være tom.");
        errors.put(NAME_LONGER_THAN_255, "Det givne navn er for langt.");
        errors.put(EMPTY_DESCRIPTION, "Den givne beskrivelse må ikke være tom.");
    }

    @Override protected void before(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setAttribute("navigation", "administration_roofings");
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Tage");
            request.setAttribute("roofings", roofingFacade.get());
            request.setAttribute("types", EnumSet.allOf(RoofingType.class));
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

            Roofing roofing = roofingFacade.first(where(eq(ID, parameters.getInt("id"))));
            if (roofing == null) {
                notifications.error("Unknown roofing.");
                response.sendRedirect("roofings");
                return;
            }

            List<Component> components = roofingFacade.getComponents(roofing.getId());
            int[]           categories = new int[components.size()];
            for (int x = 0; x < components.size(); x++)
                categories[x] = components.get(x).getDefinition().getCategory().getId();

            request.setAttribute("title", "Opdater tag");
            request.setAttribute("roofing", roofing);
            request.setAttribute("components", components);
            request.setAttribute("materials", materialFacade.getByCategory(categories).asMap());
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
                    !parameters.isEnum("type", RoofingType.class) ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("roofings");
                return;
            }

            List<ComponentReference>  values     = new ArrayList<>();
            List<ComponentDefinition> components = roofingFacade.getComponentDefinitions(parameters.getEnum("type", RoofingType.class));
            for (ComponentDefinition definition : components) {
                String parameterName = "component_" + definition.getIdentifier();
                if (!parameters.isInt(parameterName)) {
                    notifications.error("Missing component " + definition.getIdentifier());
                    response.sendRedirect("roofings");
                    return;
                }

                values.add(ComponentReference.from(definition.getId(), parameters.getInt(parameterName)));
            }

            try {
                roofingFacade.update(
                        parameters.getInt("id"),
                        parameters.value("name"),
                        parameters.value("description"),
                        parameters.getBoolean("active"),
                        values);

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
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isEnum("type", RoofingType.class)) {
                notifications.error("No roofing type provided.");
                response.sendRedirect("roofings");
                return;
            }

            List<ComponentDefinition> components = roofingFacade.getComponentDefinitions(parameters.getEnum("type", RoofingType.class));
            int[]                     categories = new int[components.size()];
            for (int x = 0; x < components.size(); x++)
                categories[x] = components.get(x).getCategory().getId();

            request.setAttribute("title", "Opret tag");
            request.setAttribute("type", request.getParameter("type"));
            request.setAttribute("components", components);
            request.setAttribute("materials", materialFacade.getByCategory(categories).asMap());
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
                    !parameters.isEnum("type", RoofingType.class) ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("roofings");
                return;
            }

            List<ComponentReference>  values     = new ArrayList<>();
            List<ComponentDefinition> components = roofingFacade.getComponentDefinitions(parameters.getEnum("type", RoofingType.class));
            for (ComponentDefinition definition : components) {
                String parameterName = "component_" + definition.getIdentifier();
                if (!parameters.isInt(parameterName)) {
                    notifications.error("Missing component " + definition.getIdentifier());
                    response.sendRedirect("roofings");
                    return;
                }

                values.add(ComponentReference.from(definition.getId(), parameters.getInt(parameterName)));
            }

            try {
                Roofing roofing = roofingFacade.create(
                        parameters.value("name"),
                        parameters.value("description"),
                        parameters.getEnum("type", RoofingType.class),
                        parameters.getBoolean("active"),
                        values);

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