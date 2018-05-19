package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentReference;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.models.Model;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.ModelFacade;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet("/administration/models")
public class AdministrationModelsServlet extends AdministrationServlet
{

    private final ModelFacade    modelFacade    = Facades.skeletonFacade;
    private final MaterialFacade materialFacade = Facades.materialFacade;

    public AdministrationModelsServlet()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("update", new HandleUpdateCommand());
        dispatcher.get("update_components", new ShowUpdateComponentsCommand());
        dispatcher.post("update_components", new HandleUpdateComponentsCommand());
    }

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.HEAD_OF_MATERIALS)) {
            notifications.error("Du skal være materialeansvarlig for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_models");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            notifications(request);
            request.setAttribute("title", "Modeller");
            request.setAttribute("models", modelFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/show_models.jsp").forward(request, response);
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
                response.sendRedirect("models");
                return;
            }

            Model model = modelFacade.get(parameters.getInt("id"));
            if (model == null) {
                notifications.error("Unknown roofing.");
                response.sendRedirect("models");
                return;
            }

            List<Component> components = modelFacade.getComponents(model.getId());
            int[]           categories = new int[components.size()];
            for (int x = 0; x < components.size(); x++)
                categories[x] = components.get(x).getDefinition().getCategory().getId();

            request.setAttribute("title", "Opdater tag");
            request.setAttribute("model", model);
            request.setAttribute("components", components);
            request.setAttribute("materials", materialFacade.getByCategory(categories).asMap());
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/update_model.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("id") ||
                    !parameters.isPresent("name")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("models");
                return;
            }

            List<ComponentReference>  values     = new ArrayList<>();
            List<ComponentDefinition> components = modelFacade.getComponentDefinitions(parameters.getInt("id"));
            for (ComponentDefinition definition : components) {
                String parameterName = "component_" + definition.getIdentifier();
                if (!parameters.isInt(parameterName)) {
                    notifications.error("Missing component " + definition.getIdentifier());
                    response.sendRedirect("models");
                    return;
                }

                values.add(ComponentReference.from(definition.getId(), parameters.getInt(parameterName)));
            }

            try {
                modelFacade.update(parameters.getInt("id"), parameters.value("name"), values);

                notifications.success("Taget blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (ApplicationException e) {
                notifications.error("An error occured.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
                return;
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

            if (!parameters.isInt("model")) {
                notifications.error("No model id provided.");
                response.sendRedirect("models");
                return;
            }

            request.setAttribute("title", "Opdater model componenter");
            request.setAttribute("definitions", modelFacade.getComponentDefinitions(parameters.getInt("model")));
            request.setAttribute("csrf", csrf(request));
            request.getRequestDispatcher("/WEB-INF/administration/update_model_components.jsp").forward(request, response);
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

            if (!parameters.isInt("model")) {
                notifications.error("No model id provided.");
                response.sendRedirect("models");
                return;
            }

            List<ComponentDefinition> definitions = modelFacade.getComponentDefinitions(parameters.getInt("model"));
            for (ComponentDefinition definition : definitions) {
                String inputName = "component_notes_" + definition.getIdentifier();
                if (!parameters.isPresent(inputName)) {
                    notifications.error("No " + inputName + " sent");
                    response.sendRedirect("?action=update_components&model=" + parameters.getInt("model"));
                    return;
                }

                definition.setNotes(parameters.value(inputName));
            }

            modelFacade.update(definitions);
            notifications.success("Komponenterne blev opdateret med success.");
            response.sendRedirect("?action=update_components&model=" + parameters.getInt("model"));
        }
    }
}
