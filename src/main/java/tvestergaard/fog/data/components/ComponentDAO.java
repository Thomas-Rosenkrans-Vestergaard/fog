package tvestergaard.fog.data.components;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface ComponentDAO
{

    /**
     * Retrieves the component definition with the provided id.
     *
     * @param id The id of the component definition to retrieve.
     * @return The object representing the retrieved component definition.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    ComponentDefinition get(int id) throws DataAccessException;

    /**
     * Inserts a new component from the provided blueprint.
     *
     * @param blueprint The blueprint to create the component from.
     * @return The object representing the inserted component.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    ComponentDefinition create(ComponentDefinitionBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the provided component definitions.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(List<ComponentDefinition> definitions) throws DataAccessException;
}
