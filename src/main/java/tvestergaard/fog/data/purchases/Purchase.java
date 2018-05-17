package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.purchases.bom.Bom;

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

    Bom getBom();

    /**
     * Returns the moment in time when the purchase was created.
     *
     * @return The moment in time when the purchase was created.
     */
    LocalDateTime getCreatedAt();
}
