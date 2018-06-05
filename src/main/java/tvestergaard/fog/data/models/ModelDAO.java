package tvestergaard.fog.data.models;

import com.google.common.collect.Multimap;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentConnection;
import tvestergaard.fog.data.materials.SimpleMaterial;

import java.util.List;

public interface ModelDAO
{

    /**
     * Returns a complete list of the garage models available.
     *
     * @return The complete list.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Model> get() throws DataAccessException;

    /**
     * Updates a model in the application.
     *
     * @param updater    The updater containing the information needed to update the model.
     * @param components The new components of the model.
     * @return {@code true} if the model was successfully updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(ModelUpdater updater, List<ComponentConnection> components) throws DataAccessException;

    /**
     * Returns the model with the provided id.
     *
     * @param id The id of the model to find.
     * @return The object representing the model with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Model get(int id) throws DataAccessException;

    /**
     * Returns the component definitions for the provided garage model.
     *
     * @param model The garage model to return the component definitions for.
     * @return The component definitions for the garage skeleton.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<ComponentDefinition> getComponentDefinitions(int model) throws DataAccessException;

    /**
     * Returns the active components for the provided garage model.
     *
     * @param model The garage model to return the component of.
     * @return The list of the active components for the garage skeleton.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Component> getComponents(int model) throws DataAccessException;

    /**
     * Returns the material choices for the provided garage model.
     *
     * @param model The garage model to return the material choices for.
     * @return Returns the material choices for the components of the garage skeleton.The material is then mapped to the
     * id of the component.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Multimap<Integer, SimpleMaterial> getComponentMaterials(int model) throws DataAccessException;
}
