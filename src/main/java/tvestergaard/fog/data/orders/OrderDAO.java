package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.orders.OrderColumn.ID;

public interface OrderDAO
{

    /**
     * Returns the orders in the data storage.
     *
     * @return The resulting orders.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default List<Order> get() throws DataAccessException
    {
        return get(new Constraints<>());
    }

    /**
     * Returns the order with the provided id.
     *
     * @param id The id of the order to return.
     * @return The order with the provided id. Returns {@code null} if no such order exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Order get(int id) throws DataAccessException
    {
        return first(where(eq(ID, id)));
    }

    /**
     * Returns the orders in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Order> get(Constraints<OrderColumn> constraints) throws DataAccessException;

    /**
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Order first(Constraints<OrderColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new order into data storage.
     *
     * @param blueprint The order blueprint that contains the information necessary to create the order.
     * @return The instance representing the newly created order.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    Order create(OrderBlueprint blueprint) throws DataAccessException;

    /**
     * Cancels the order with the provided id. The order is then marked inactive.
     *
     * @param order The id of the order to cancel.
     * @return {@code true} if the order was cancelled.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    boolean cancel(int order) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code order}.
     *
     * @param updater The order updater that contains the information necessary to create the order.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(OrderUpdater updater) throws DataAccessException;

    /**
     * Returns the number of orders that are both active, and have not yet received any offers.
     *
     * @return The number of such orders.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int getNumberOfNewOrders() throws DataAccessException;
}
