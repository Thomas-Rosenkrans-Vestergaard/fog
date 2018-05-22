package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentReference;
import tvestergaard.fog.data.models.Model;
import tvestergaard.fog.data.models.ModelDAO;
import tvestergaard.fog.data.models.ModelUpdater;

import java.util.List;

public class ModelFacade
{

    /**
     * The model dao used to access the models in the application.
     */
    private final ModelDAO modelDAO;

    /**
     * Creates a new {@link ModelFacade}.
     *
     * @param modelDAO The model dao used to access the models in the application.
     */
    public ModelFacade(ModelDAO modelDAO)
    {
        this.modelDAO = modelDAO;
    }

    /**
     * Returns a complete list of the garage models available.
     *
     * @return The complete list.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Model> get()
    {
        try {
            return modelDAO.get();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the model with the provided id.
     *
     * @param id The id of the model to find.
     * @return The object representing the model with the provided id.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Model get(int id)
    {
        try {
            return modelDAO.get(id);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the model with the provided id.
     *
     * @param id         The id of the model to update.
     * @param name       The new name of the model.
     * @param components The components.
     * @return {@code true} if the model was successfully updated.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public boolean update(int id, String name, List<ComponentReference> components)
    {
        try {
            ModelUpdater updater = ModelUpdater.from(id, name);
            return modelDAO.update(updater, components);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the component definitions for a model.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public boolean update(List<ComponentDefinition> definitions)
    {
        try {
            return modelDAO.update(definitions);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the component definitions for the provided garage model.
     *
     * @param model The garage model to return the component definitions for.
     * @return The component definitions for the garage skeleton.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<ComponentDefinition> getComponentDefinitions(int model)
    {
        try {
            return modelDAO.getComponentDefinitions(model);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the active components for the provided garage model.
     *
     * @param model The garage model to return the component of.
     * @return The list of the active components for the garage skeleton.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Component> getComponents(int model)
    {
        try {
            return modelDAO.getComponents(model);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
