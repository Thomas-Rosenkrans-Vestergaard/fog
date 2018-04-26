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
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Flooring> get(Constraint<FlooringColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first flooring matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first flooring matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Flooring first(Constraint<FlooringColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new flooring into the data storage.
     *
     * @param name                The name of the flooring to create.
     * @param description         The description of the flooring to create.
     * @param pricePerSquareMeter The price per square meter of flooring (in øre).
     * @param active              Whether or not the flooring can be applied to orders.
     * @return The flooring instance representing the newly created flooring.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Flooring create(String name, String description, int pricePerSquareMeter, boolean active)
            throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code flooring}.
     *
     * @param flooring The flooring to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Flooring flooring) throws DataAccessException;
}
