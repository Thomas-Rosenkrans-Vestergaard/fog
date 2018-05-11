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
                             ShedBlueprint shed)
    {
        return new OrderRecord(id, customer, null, width, length, height, roofing, null, slope, rafterChoice,
                new ShedRecord(-1, shed.getDepth(), shed.getCladdingId(), null, shed.getFlooringId(), null), false, -1,
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
    ShedUpdater getShed();

    /**
     * Returns whether or not the order is considered active.
     *
     * @return {@code true} if the order is considered active.
     */
    boolean isActive();
}
