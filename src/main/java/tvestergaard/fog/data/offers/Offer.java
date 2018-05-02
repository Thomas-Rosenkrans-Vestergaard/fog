package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.orders.Order;

import java.time.LocalDateTime;

public interface Offer extends OfferBlueprint
{

    /**
     * Returns the order the offer was issued for.
     *
     * @return The order the offer was issued for.
     */
    Order getOrder();

    /**
     * Returns the employee who created the offer.
     *
     * @return The employee who created the offer.
     */
    Employee getEmployee();

    /**
     * Returns the moment in time when the offer was created.
     *
     * @return The moment in time when the offer was created.
     */
    LocalDateTime getCreatedAt();
}
