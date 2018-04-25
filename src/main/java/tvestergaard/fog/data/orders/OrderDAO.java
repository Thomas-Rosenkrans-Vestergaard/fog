package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.data.sheds.Shed;

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


    /**
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no
     * constraints matches the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Order first(Constraint<OrderColumn>... constraints) throws DataAccessException;
    
    /**
     * Inserts a new order into data storage.
     *
     * @param customer The customer who placed the order.
     * @param type     The type of order to place.
     * @param cladding The cladding used on the order.
     * @param width    The width of the order.
     * @param length   The length of the order.
     * @param height   The height of the order.
     * @param roofing  The roofing used on the order.
     * @param slope    The slope of the roofing used on the order.
     * @param rafters  The rafters construction deivered with the order.
     * @param shed     The shed to add to the order.
     * @return The new order.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    Order create(
            Customer customer,
            Order.Type type,
            Cladding cladding,
            int width,
            int length,
            int height,
            Roofing roofing,
            int slope,
            Order.Rafters rafters,
            Shed shed
    ) throws DataAccessException;
}
