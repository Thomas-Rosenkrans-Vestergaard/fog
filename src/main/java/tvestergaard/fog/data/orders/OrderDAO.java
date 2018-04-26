package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.sheds.ShedSpecification;

import java.util.List;

public interface OrderDAO
{

    /**
     * Returns the orders in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Order> get(Constraint<OrderColumn>... constraints) throws DataAccessException;


    /**
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Order first(Constraint<OrderColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new order into data storage.
     *
     * @param customer The id of the customer who placed the order.
     * @param cladding The id of the cladding used on the order.
     * @param width    The width of the order.
     * @param length   The length of the order.
     * @param height   The height of the order.
     * @param roofing  The id of the roofing used on the order.
     * @param slope    The slope of the roofing used on the order.
     * @param rafters  The rafters construction delivered with the order.
     * @param shed     The shed to add to the order.
     * @return The new order.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    Order create(
            int customer,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            RafterChoice rafters,
            ShedSpecification shed) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code order}.
     *
     * @param order The order to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Order order) throws DataAccessException;
}
