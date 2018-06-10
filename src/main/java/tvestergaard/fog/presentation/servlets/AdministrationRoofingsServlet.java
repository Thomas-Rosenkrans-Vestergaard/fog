package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentConnection;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.data.roofing.RoofingType;
import tvestergaard.fog.logic.ComponentFacade;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.roofings.RoofingError;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.logic.roofings.RoofingValidatorException;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.TableControls;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static tvestergaard.fog.data.roofing.RoofingColumn.*;
import static tvestergaard.fog.logic.roofings.RoofingError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/administration/roofings")
public class AdministrationRoofingsServlet extends AdministrationServlet
{

    private final RoofingFacade             roofingFacade   = Facades.roofingFacade;
    private final MaterialFacade            materialFacade  = Facades.materialFacade;
    private final ComponentFacade           componentFacade = Facades.componentFacade;
    private final Map<RoofingError, String> errors          = new HashMap<>();

    public AdministrationRoofingsServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("create", new HandleCreateCommand());
        dispatcher.post("update", new HandleUpdateCommand());
        dispatcher.get("update_components", new ShowUpdateComponentsCommand());
        dispatcher.post("update_components", new HandleUpdateComponentsCommand());

        errors.put(EMPTY_NAME, "Det givne navn må ikke være tom.");
        errors.put(NAME_LONGER_THAN_255, "Det givne navn er for langt.");
        errors.put(EMPTY_DESCRIPTION, "Den givne beskrivelse må ikke være tom.");
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.HEAD_OF_MATERIALS)) {
            notifications.error("Du skal være materialeansvarlig for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_roofings");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<RoofingColumn> controls = new TableControls<>(request);
            controls.add(NAME, TableControls.Type.TEXT);
            controls.add(DESCRIPTION, TableControls.Type.TEXT);
            controls.add(ACTIVE, TableControls.Type.BOOLEAN);
            controls.add(TYPE, TableControls.Type.TEXT);

            notifications(request);
            request.setAttribute("title", "Tage");
            request.setAttribute("roofings", roofingFacade.get(controls.constraints()));
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

            Roofing roofing = roofingFacade.get(parameters.getInt("id"));
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
            request.setAttribute("csrf", csrf(request));
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

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
                return;
            }

            List<ComponentConnection> values     = new ArrayList<>();
            List<ComponentDefinition> components = roofingFacade.getComponentDefinitions(parameters.getEnum("type", RoofingType.class));
            for (ComponentDefinition definition : components) {
                String parameterName = "component_" + definition.getIdentifier();
                if (!parameters.isInt(parameterName)) {
                    notifications.error("Missing component " + definition.getIdentifier());
                    response.sendRedirect("roofings");
                    return;
                }

                values.add(ComponentConnection.from(definition.getId(), parameters.getInt(parameterName)));
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
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/create_roofing.jsp").forward(request, response);
        }
    }

    private class HandleCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("?action=create");
                return;
            }

            if (!parameters.isPresent("name") ||
                    !parameters.isPresent("description") ||
                    !parameters.isEnum("type", RoofingType.class) ||
                    !parameters.isBoolean("active")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("roofings");
                return;
            }

            List<ComponentConnection> values     = new ArrayList<>();
            List<ComponentDefinition> components = roofingFacade.getComponentDefinitions(parameters.getEnum("type", RoofingType.class));
            for (ComponentDefinition definition : components) {
                String parameterName = "component_" + definition.getIdentifier();
                if (!parameters.isInt(parameterName)) {
                    notifications.error("Missing component " + definition.getIdentifier());
                    response.sendRedirect("roofings");
                    return;
                }

                values.add(ComponentConnection.from(definition.getId(), parameters.getInt(parameterName)));
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


    private class ShowUpdateComponentsCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isEnum("roofing", RoofingType.class)) {
                notifications.error("No roofing type provided.");
                response.sendRedirect("roofings");
                return;
            }

            request.setAttribute("title", "Opdater roofing komponenter");
            request.setAttribute("definitions", roofingFacade.getComponentDefinitions(parameters.getEnum("roofing", RoofingType.class)));
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/update_roofing_components.jsp").forward(request, response);
        }
    }

    private class HandleUpdateComponentsCommand implements Command
    {

        /**
         * Delegates the request and response objects to this command.
         *
         * @param request  The request.
         * @param response The response.
         */
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isEnum("roofing", RoofingType.class)) {
                notifications.error("No roofing type provided.");
                response.sendRedirect("roofings");
                return;
            }

            if (!verify(request)) {
                notifications.error("Token udløbet.");
                response.sendRedirect("roofings");
                return;
            }

            List<ComponentDefinition> definitions = roofingFacade.getComponentDefinitions(parameters.getEnum("roofing", RoofingType.class));
            for (ComponentDefinition definition : definitions) {
                String inputName = "component_notes_" + definition.getIdentifier();
                if (!parameters.isPresent(inputName)) {
                    notifications.error("No " + inputName + " sent");
                    response.sendRedirect("?action=update_components&roofing=" + parameters.value("roofing"));
                    return;
                }

                definition.setNotes(parameters.value(inputName));
            }

            componentFacade.update(definitions);
            notifications.success("Komponenterne blev opdateret med success.");
            response.sendRedirect("?action=update_components&roofing=" + parameters.value("roofing"));
        }
    }
}
