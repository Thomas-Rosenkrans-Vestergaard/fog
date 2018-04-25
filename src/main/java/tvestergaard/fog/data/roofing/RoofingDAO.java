package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface RoofingDAO
{

    /**
     * Returns the roofings in the data storage.
     * The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the roofings in the data storage.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Roofing> get(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no
     * constraints matches the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Roofing first(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param name                The name of the roofing to create.
     * @param description         The description of the roofing to create.
     * @param minimumSlope        The minimum slope at which the roofing to create can be laid.
     * @param maximumSlope        The maximum slope at which the roofing to create can be laid.
     * @param pricePerSquareMeter The price per square meter of roofing (in øre).
     * @param active              Whether or not the roofing can be applied to orders.
     * @return The roofing instance representing the newly created roofing.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Roofing create(String name, String description, int minimumSlope, int maximumSlope, int pricePerSquareMeter, boolean active)
            throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param roofing The roofing to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Roofing roofing) throws DataAccessException;
}
