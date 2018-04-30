package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;

import java.time.LocalDateTime;

public interface Order extends OrderUpdater
{

    /**
     * Returns the customer who placed the order.
     *
     * @return The customer who placed the order.
     */
    Customer getCustomer();

    /**
     * Returns the cladding used on the order.
     *
     * @return The cladding used on the order.
     */
    Cladding getCladding();

    /**
     * Returns the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    Roofing getRoofing();

    /**
     * Returns the shed installed in the order.
     *
     * @return The shed installed in the order.
     */
    @Override Shed getShed();

    /**
     * Returns the number of offers that have been made regarding this order.
     *
     * @return The number of offsets that have been made regarding this order.
     */
    int getNumberOfOffers();

    /**
     * Returns a {@code LocalDateTime} representing the moment in time when the customer placed the {@link Order}.
     *
     * @return The {@code LocalDateTime}.
     */
    LocalDateTime getCreatedAt();

    /**
     * Checks that this order equals another provided object. The two objects are only considered equal when all the
     * attributes of the two orders are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the order.
     *
     * @return The id of the order.
     */
    int hashCode();
}
