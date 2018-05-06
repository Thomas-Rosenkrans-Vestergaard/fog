package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;
import java.util.Set;

public interface MaterialDAO
{

    /**
     * Returns the materials in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the materials in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Material> get(Constraint<MaterialColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first material matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first material matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material first(Constraint<MaterialColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new material into the data storage.
     *
     * @param blueprint The material blueprint that contains the information necessary to create the material.
     * @return The material instance representing the newly created material.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material create(MaterialBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code material}.
     *
     * @param updater The material updater that contains the information necessary to create the material.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(MaterialUpdater updater) throws DataAccessException;

    /**
     * Returns a list of the attributes required for the provided category id.
     *
     * @param category The category id to return the required attributes for.
     * @return The list of the attributes required for the provided category id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Set<AttributeDefinition> getAttributesFor(int category) throws DataAccessException;

    /**
     * Returns the complete list of the categories in the application.
     *
     * @return The complete list of the categories in the application.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Category> getCategories() throws DataAccessException;
}
