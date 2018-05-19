package tvestergaard.fog.data.purchases;

public interface PurchaseBlueprint
{

    /**
     * Creates a new {@link PurchaseBlueprint} from the provided information.
     *
     * @param offerId    The id of the offer from which to create the purchase.
     * @param employeeId The id of the employee creating the purchase.
     * @return The newly created blueprint.
     */
    static PurchaseBlueprint from(int offerId, int employeeId)
    {
        return new PurchaseRecord(-1, offerId, null, -1, employeeId, null, null);
    }

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
