package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.bom.Bom;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.offers.Offer;

import java.time.LocalDateTime;

public interface Purchase extends PurchaseBlueprint
{


    /**
     * Returns the unique identifier of the purchase.
     *
     * @return The unique identifier of the purchase.
     */
    int getId();

    /**
     * Returns the offer that was accepted.
     *
     * @return The offer that was accepted.
     */
    Offer getOffer();

    /**
     * Returns the employee who created the purchase.
     *
     * @return The employee who created the purchase.
     */
    Employee getEmployee();

    /**
     * Returns the bill of materials described by the purchase.
     *
     * @return The bill of materials described by the purchase.
     */
    Bom getBOM();

    /**
     * Returns the moment in time when the purchase was created.
     *
     * @return The moment in time when the purchase was created.
     */
    LocalDateTime getCreatedAt();
}
