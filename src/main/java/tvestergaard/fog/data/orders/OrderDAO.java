package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.contraints.Constraint;

import java.util.List;

public interface OrderDAO
{

    /**
     * Returns the {@link Order}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Order}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Order> getOrders(Constraint... constraints) throws DataAccessException;
}
