package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface OrderDAO
{

    /**
     * Returns a complete list of the {@link Order}s in the system.
     *
     * @return The complete list of the {@link Order}.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    List<Order> getOrders() throws DataAccessException;
}
