package tvestergaard.fog.data.orders;

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
     * Returns the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    Roofing getRoofing();

    /**
     * Returns the shed included with the order.
     *
     * @return The shed instance.
     */
    Shed getShed();

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
}
