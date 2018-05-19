package tvestergaard.fog.data.purchases;

public interface PurchaseBlueprint
{

    /**
     * Creates a new {@link PurchaseBlueprint} from the provided information.
     *
     * @param offerId The id of the offer from which to create the purchase.
     * @return The newly created blueprint.
     */
    static PurchaseBlueprint from(int offerId)
    {
        return new PurchaseRecord(-1, offerId, null, -1, null);
    }

    /**
     * Returns the id of the offer that was accepted.
     *
     * @return The id of the offer that was accepted.
     */
    int getOfferId();
}
