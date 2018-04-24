package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface CustomerDAO
{

    /**
     * Returns a complete list of the {@link Customer}s in the system.
     *
     * @return The complete list.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    List<Customer> get() throws DataAccessException;
}
