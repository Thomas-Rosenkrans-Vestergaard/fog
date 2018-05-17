package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.purchases.bom.BomBlueprint;

public interface PurchaseBlueprint
{

    static PurchaseBlueprint from(int offerId, int employeeId, BomBlueprint bomBlueprint)
    {
        return new PurchaseRecord(-1, offerId, null, employeeId, null, bomBlueprint, null, null);
    }

    BomBlueprint getBomBlueprint();

    /**
     * Returns the id of the offer that was accepted.
     *
     * @return The id of the offer that was accepted.
     */
    int getOfferId();

    /**
     * Returns the id of the employee who created the purchase.
     *
     * @return The id of the employee who created the purchase.
     */
    int getEmployeeId();
}
