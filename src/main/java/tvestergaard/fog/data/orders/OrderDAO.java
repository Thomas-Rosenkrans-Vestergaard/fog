package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface OrderDAO
{

    /**
     * Returns the orders in the data storage.
     * The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Order> get(Constraint<OrderColumn>... constraints) throws DataAccessException;
}
