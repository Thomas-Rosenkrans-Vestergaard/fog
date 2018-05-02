package tvestergaard.fog.data.offers;

public interface OfferBlueprint
{

    /**
     * Creates a new offer blueprint from the provided information.
     *
     * @param order    The id of the order the offer is issued for.
     * @param employee The id of the employee who placed the offer.
     * @param price    The price of the offer.
     * @return The resulting offer blueprint.
     */
    static OfferBlueprint from(int order, int employee, int price)
    {
        return new OfferRecord(-1, null, order, null, employee, price, null);
    }

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
