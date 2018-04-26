package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface CladdingDAO
{

    /**
     * Returns the claddings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the claddings in the data storage.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Cladding> get(Constraint<CladdingColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first cladding matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first cladding matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Cladding first(Constraint<CladdingColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new cladding into the data storage.
     *
     * @param name                The name of the cladding to create.
     * @param description         The description of the cladding to create.
     * @param pricePerSquareMeter The price per square meter of cladding (in øre).
     * @param active              Whether or not the cladding can be applied to orders.
     * @return The cladding instance representing the newly created cladding.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Cladding create(String name, String description, int pricePerSquareMeter, boolean active)
            throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param cladding The cladding to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Cladding cladding) throws DataAccessException;
}
