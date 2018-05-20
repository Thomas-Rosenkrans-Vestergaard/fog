package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.MaterialColumn;
import tvestergaard.fog.data.materials.attributes.AttributeDefinition;
import tvestergaard.fog.data.materials.attributes.AttributeValue;
import tvestergaard.fog.data.materials.attributes.DataType;
import tvestergaard.fog.data.materials.attributes.DefaultAttributeValue;
import tvestergaard.fog.logic.materials.MaterialError;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.materials.MaterialValidatorException;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;
import tvestergaard.fog.presentation.servlets.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static tvestergaard.fog.logic.materials.MaterialError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/materials")
public class AdministrationMaterialsServlet extends AdministrationServlet
{

    private final MaterialFacade             materialsFacade = Facades.materialFacade;
    private final Map<MaterialError, String> errors          = new HashMap<>();

    public AdministrationMaterialsServlet()
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

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);
        if (!employee.is(Role.HEAD_OF_MATERIALS)) {
            notifications.error("Du skal være materialeansvarlig for at tilgå denne side.");
            resp.sendRedirect("index");
            return false;
        }

        req.setAttribute("navigation", "administration_materials");
        return true;
    }

    private class ShowTableCommand implements Command
    {
        @Override public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            TableControls<MaterialColumn> controls = new TableControls<>(request, MaterialColumn.class, MaterialColumn.SEARCH);

            notifications(request);
            request.setAttribute("title", "Materialee");
            request.setAttribute("materials", materialsFacade.get(controls.constraints()));
            request.setAttribute("categories", materialsFacade.getCategories());
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
            request.setAttribute("attributes", materialsFacade.getAttributesFor(parameters.getInt("category")));
            request.setAttribute("csrf", csrf(request));
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
                    !parameters.isFloat("price") ||
                    !parameters.isInt("unit") ||
                    !parameters.isInt("category")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("materials");
                return;
            }

            Set<AttributeDefinition> attributeDefinitions = materialsFacade.getAttributesFor(parameters.getInt("category"));
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
                Material material = materialsFacade.create(
                        parameters.value("number"),
                        parameters.value("description"),
                        Math.round(parameters.getFloat("price") * 100),
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

            Material material = materialsFacade.get(parameters.getInt("id"));
            if (material == null) {
                notifications.error("Unknown material.");
                response.sendRedirect("materials");
                return;
            }

            request.setAttribute("title", "Opdater materiale");
            request.setAttribute("material", material);
            request.setAttribute("csrf", csrf(request));
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
                    !parameters.isFloat("price") ||
                    !parameters.isInt("unit") ||
                    !parameters.isInt("category")) {
                notifications.error("Cannot format parameters.");
                response.sendRedirect("materials");
                return;
            }

            Set<AttributeDefinition> attributeDefinitions = materialsFacade.getAttributesFor(parameters.getInt("category"));
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
                Material material = materialsFacade.update(
                        parameters.getInt("id"),
                        parameters.value("number"),
                        parameters.value("description"),
                        Math.round(parameters.getFloat("price") * 100),
                        parameters.getInt("unit"),
                        parameters.getInt("category"),
                        attributes);

                notifications.success("Materialeet blev opdateret.");
                response.sendRedirect("?action=update&id=" + material.getId());

            } catch (MaterialValidatorException e) {
                for (MaterialError error : e.getErrors())
                    notifications.error(errors.get(error));
                response.sendRedirect("?action=update&id=" + parameters.getInt("id"));
            }
        }
    }
}
