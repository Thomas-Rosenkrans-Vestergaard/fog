package tvestergaard.fog.data.purchases;

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
     * Returns the id of the bom.
     *
     * @return The id of the bom.
     */
    int getBomId();

    /**
     * Returns the moment in time when the purchase was created.
     *
     * @return The moment in time when the purchase was created.
     */
    LocalDateTime getCreatedAt();
}
