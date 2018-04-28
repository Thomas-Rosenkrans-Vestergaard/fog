package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface MaterialDAO
{

    /**
     * Returns the materials in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the materials in the data storage.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Material> get(Constraint<MaterialColumn>... constraints) throws DataAccessException;

    /**
     * Returns a list of the active materials (those that are used during construction).
     *
     * @return The active materials (those that are used during construction)l.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Material> getActive() throws DataAccessException;

    /**
     * Returns the first material matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first material matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Material first(Constraint<MaterialColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new material into the data storage.
     *
     * @param blueprint The material blueprint that contains the information necessary to create the material.
     * @return The material instance representing the newly created material.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Material create(MaterialBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code material}.
     *
     * @param updater The material updater that contains the information necessary to create the material.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(MaterialUpdater updater) throws DataAccessException;
}
