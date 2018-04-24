package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface CustomerDAO
{

    /**
     * Returns the {@link Customer}s in the data storage.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Customer}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Customer> get(Constraint... constraints) throws DataAccessException;
}
