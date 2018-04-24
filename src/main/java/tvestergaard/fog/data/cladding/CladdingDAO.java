package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface CladdingDAO
{

    /**
     * Returns the {@link Cladding}s in the data storage.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The complete list of the {@link Cladding}s in the data storage.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Cladding> get(Constraint... constraints) throws DataAccessException;

    /**
     * Returns the first cladding matching the provided {@link Constraint}s.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first {@link Cladding} matching the provided {@link Constraint}s. Returns {@code null} when no
     * {@link Constraint}s matches the provided {@link Constraint}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Cladding first(Constraint... constraints) throws DataAccessException;

    /**
     * Inserts a new {@link Cladding} into the data storage.
     *
     * @param name                The name of the {@link Cladding} to create.
     * @param description         The description of the {@link Cladding} to create.
     * @param pricePerSquareMeter The price per square meter of {@link Cladding} (in øre).
     * @param active              Whether or not the {@link Cladding} can be applied to orders.
     * @return The {@link Cladding} instance representing the newly created {@link Cladding}.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Cladding create(String name, String description, int pricePerSquareMeter, boolean active) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param cladding The {@link Cladding} to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Cladding cladding) throws DataAccessException;
}
