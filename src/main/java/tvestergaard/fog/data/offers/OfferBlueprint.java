package tvestergaard.fog.data.offers;

public interface OfferBlueprint
{

    /**
     * Returns the id of the order that the offer was issued for.
     *
     * @return The id of the order that the offer was issued for.
     */
    int getOrderId();

    /**
     * Returns the id of the employee who created the offer.
     *
     * @return The id of the employee who created the offer.
     */
    int getEmployeeId();

    /**
     * Returns the price of the offer.
     *
     * @return The price of the offer.
     */
    int getPrice();
}
