package tvestergaard.fog.data.orders;

public interface OrderUpdater extends OrderBlueprint
{

    /**
     * Returns a new order updater from the provided information.
     *
     * @param id           The id of the order to update.
     * @param customer     The customer of the order to specify in the updater.
     * @param width        The width of the order to specify in the updater.
     * @param length       The length of the order to specify in the updater.
     * @param height       The height of the order to specify in the updater.
     * @param roofing      The roofing of the order to specify in the updater.
     * @param slope        The slope of the order to specify in the updater.
     * @param rafterChoice The choice of rafters on the order to specify in the updater.
     * @param active       The active status of the order in the updater.
     * @param shedUpdater  The shed to include in the order. {@code null} when the order has no shed.
     * @param comment      The comment by the customer about the order.
     * @return The newly created order updater.
     */
    static OrderUpdater from(int id,
                             int customer,
                             int width,
                             int length,
                             int height,
                             int roofing,
                             int slope,
                             RafterChoice rafterChoice,
                             boolean active,
                             ShedUpdater shedUpdater,
                             String comment)
    {
        return new OrderRecord(id, customer, null, width, length, height, roofing, null, slope, rafterChoice,
                shedUpdater, shedUpdater, null, comment, active, -1, -1,
                null);
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return The unique identifier of the order.
     */
    int getId();

    /**
     * Returns the shed updater.
     *
     * @return The shed updater.
     */
    ShedUpdater getShedUpdater();

    /**
     * Returns whether or not the order is considered active.
     *
     * @return {@code true} if the order is considered active.
     */
    boolean isActive();
}
