package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.Components;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface RoofingDAO
{

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the roofings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Roofing> get(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Roofing first(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param blueprint The roofing blueprint that contains the information necessary to create the roofing.
     * @return The roofing instance representing the newly created roofing.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Roofing create(RoofingBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param updater The roofing updater that contains the information necessary to create the roofing.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(RoofingUpdater updater) throws DataAccessException;

    /**
     * Returns the components for the roofing with the provided id.
     *
     * @param roofing The id of the roofing to return the components of.
     * @return The components for the roofing with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Components getComponentsFor(int roofing) throws DataAccessException;
}
