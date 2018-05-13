package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface FlooringDAO
{

    /**
     * Returns the floorings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting floorings.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Flooring> get(Constraint<FlooringColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first flooring matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first flooring matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Flooring first(Constraint<FlooringColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new flooring into the data storage.
     *
     * @param blueprint The flooring blueprint that contains the information necessary to create the flooring.
     * @return The flooring instance representing the newly created flooring.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Flooring create(FlooringBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code flooring}.
     *
     * @param updater The flooring updater that contains the information necessary to create the flooring.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(FlooringUpdater updater) throws DataAccessException;

    /**
     * Returns the number of floorings in the data storage.
     *
     * @return The number of floorings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int size() throws DataAccessException;
}
