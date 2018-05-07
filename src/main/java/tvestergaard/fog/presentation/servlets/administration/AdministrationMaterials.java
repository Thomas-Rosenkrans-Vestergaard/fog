package tvestergaard.fog.presentation.servlets.administration;

import tvestergaard.fog.data.materials.*;
import tvestergaard.fog.logic.materials.MaterialError;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.materials.MaterialValidatorException;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.materials.MaterialColumn.ID;
import static tvestergaard.fog.logic.materials.MaterialError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/materials")
public class AdministrationMaterials extends HttpServlet
{

    private final MaterialFacade             facade     = Facades.materialFacade;
    private final CommandDispatcher          dispatcher = new CommandDispatcher();
    private final Map<MaterialError, String> errors     = new HashMap<>();

    public AdministrationMaterials()
    {
        dispatcher.get(null, new ShowTableCommand());
        dispatcher.get("update", new ShowUpdateCommand());
        dispatcher.post("update", new HandleUpdateCommand());
        dispatcher.get("create", new ShowCreateCommand());
        dispatcher.post("create", new HandleCreateCommand());

        errors.put(NUMBER_EMPTY, "Materialenummer er tomt.");
        errors.put(NUMBER_LONGER_THAN_12, "Materialet er længere end 12.");
        errors.put(DESCRIPTION_EMPTY, "Beskrivelsen må ikke være tom.");
        errors.put(DESCRIPTION_LONGER_THAN_255, "Beskrivelsen må ikke være længere end 255.");
        errors.put(UNIT_LESS_THAN_1, "Enheden er mindre end 1.");
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
            request.setAttribute("title", "Materialee");
            request.setAttribute("materials", facade.get());
            request.setAttribute("categories", facade.getCategories());
            request.getRequestDispatcher("/WEB-INF/administration/show_materials.jsp").forward(request, response);
        }
    }

    private class ShowCreateCommand implements Command
    {

        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isInt("category")) {
                notifications.error("No category provided.");
                response.sendRedirect("materials");
                return;
            }

            request.setAttribute("title", "Opret materiale");
            request.setAttribute("category", parameters.getInt("category"));
            request.setAttribute("attributes", facade.getAttributesFor(parameters.getInt("category")));
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
                    !parameters.isPresent("price") ||
                    !parameters.isInt("unit") ||
                    !parameters.isInt("category")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("materials");
                return;
            }

            Set<AttributeDefinition> attributeDefinitions = facade.getAttributesFor(parameters.getInt("category"));
            Set<AttributeValue>      attributes           = new HashSet<>();
            for (AttributeDefinition definition : attributeDefinitions) {
                DataType dataType      = definition.getDataType();
                String   attributeName = "attribute_" + definition.getName();
                if (dataType == DataType.INT) {
                    if (!parameters.isInt(attributeName))
                        notifications.error("Bad format for attribute " + definition.getName());
                    attributes.add(new DefaultAttributeValue(definition, parameters.getInt(attributeName)));
                } else {
                    if (!parameters.isPresent(attributeName))
                        notifications.error("Bad format for attribute " + definition.getName());
                    attributes.add(new DefaultAttributeValue(definition, parameters.value(attributeName)));
                }
            }

            try {
                Material material = facade.create(
                        parameters.value("number"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getInt("unit"),
                        parameters.getInt("category"),
                        attributes);

                notifications.success("Materialeet blev oprettet.");
                response.sendRedirect("?action=update&id=" + material.getId());

            } catch (MaterialValidatorException e) {
                for (MaterialError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=create");
            }
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
                response.sendRedirect("materials");
                return;
            }

            Material material = facade.first(where(eq(ID, parameters.getInt("id"))));
            if (material == null) {
                notifications.error("Unknown material.");
                response.sendRedirect("materials");
                return;
            }

            request.setAttribute("title", "Opdater materiale");
            request.setAttribute("material", material);
            request.getRequestDispatcher("/WEB-INF/administration/update_material.jsp").forward(request, response);
        }
    }

    private class HandleUpdateCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            Parameters    parameters    = new Parameters(request);
            Notifications notifications = notifications(request);

            if (!parameters.isPresent("number") ||
                    !parameters.isPresent("description") ||
                    !parameters.isPresent("price") ||
                    !parameters.isInt("unit") ||
                    !parameters.isInt("category")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("materials");
                return;
            }

            Set<AttributeDefinition> attributeDefinitions = facade.getAttributesFor(parameters.getInt("category"));
            Set<AttributeValue>      attributes           = new HashSet<>();
            for (AttributeDefinition definition : attributeDefinitions) {
                DataType dataType      = definition.getDataType();
                String   attributeName = "attribute_" + definition.getName();
                if (dataType == DataType.INT) {
                    if (!parameters.isInt(attributeName))
                        notifications.error("Bad format for attribute " + definition.getName());
                    attributes.add(new DefaultAttributeValue(definition, parameters.getInt(attributeName)));
                } else {
                    if (!parameters.isPresent(attributeName))
                        notifications.error("Bad format for attribute " + definition.getName());
                    attributes.add(new DefaultAttributeValue(definition, parameters.value(attributeName)));
                }
            }

            try {
                facade.update(
                        parameters.getInt("id"),
                        parameters.value("number"),
                        parameters.value("description"),
                        parameters.getInt("price"),
                        parameters.getInt("unit"),
                        parameters.getInt("category"),
                        attributes);

                notifications.success("Materialeet blev opdateret.");
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));

            } catch (MaterialValidatorException e) {
                for (MaterialError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
            }
        }
    }
}
