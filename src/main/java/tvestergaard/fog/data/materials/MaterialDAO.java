package tvestergaard.fog.data.materials;

import com.google.common.collect.Multimap;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.materials.attributes.AttributeDefinition;

import java.util.List;
import java.util.Set;

public interface MaterialDAO
{

    /**
     * Returns the materials in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that restrict the results returned from the query.
     * @return The complete list of the materials in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Material> get(Constraints<MaterialColumn> constraints) throws DataAccessException;

    /**
     * Returns the material with the provided id.
     *
     * @param id The id of the material to return.
     * @return The material with the provided id. {@code null} in case a material with the provided id does not exist.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material get(int id) throws DataAccessException;

    /**
     * Returns the first record matching the constraints.
     *
     * @param constraints The constraints to match.
     * @return The first record matching the constraints. Returns {@code null} if no match was found.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material first(Constraints<MaterialColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new material into the data storage.
     *
     * @param blueprint The material blueprint that contains the information necessary to create the material.
     * @return The material instance representing the newly created material.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material create(MaterialBlueprint blueprint) throws DataAccessException;

    /**
     * Inserts a new material into the data storage, replacing the provided previous material.
     *
     * @param updater The blueprint containing information about the material to update.
     * @return The material instance representing the newly created material.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Material update(MaterialUpdater updater) throws DataAccessException;

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

    /**
     * Returns a complete list of the materials in the provided categories.
     *
     * @param categories The categories to return the materials from.
     * @return The materials in the provided categories, mapped to that category in the multimap.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Multimap<Integer, SimpleMaterial> getByCategory(int... categories) throws DataAccessException;
}
