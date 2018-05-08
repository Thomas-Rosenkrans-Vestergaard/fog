package tvestergaard.fog.presentation.servlets.administration;

import tvestergaard.fog.data.models.Model;
import tvestergaard.fog.data.roofing.ComponentDefinition;
import tvestergaard.fog.data.roofing.ComponentReference;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.ModelFacade;
import tvestergaard.fog.logic.roofings.RoofingError;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.Facades;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet("/administration/models")
public class AdministrationModels extends HttpServlet
{

    private final ModelFacade modelFacade = Facades.skeletonFacade;

    private final CommandDispatcher         dispatcher = new CommandDispatcher();
    private final Map<RoofingError, String> errors     = new HashMap<>();

    public AdministrationModels()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("update", new HandleUpdateCommand());
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);
        if (!authentication.isEmployee()) {
            notifications.error("Du skal være logged ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("login");
            return;
        }

        dispatcher.dispatch(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        Notifications  notifications  = notifications(req);
        if (!authentication.isEmployee()) {
            notifications.error("Du skal være logged ind som en medarbejder for at tilgå denne side.");
            resp.sendRedirect("login");
            return;
        }

        dispatcher.dispatch(req, resp);
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

            request.setAttribute("title", "Opdater tag");
            request.setAttribute("model", model);
            request.setAttribute("components", modelFacade.getComponents(model.getId()));
            request.setAttribute("materials", modelFacade.getMaterialChoices(model.getId()).asMap());
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
}