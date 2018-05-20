package tvestergaard.fog.data.orders;

public interface OrderBlueprint
{

    /**
     * Returns a new order blueprint from the provided information.
     *
     * @param customer      The customer of the order to specify in the blueprint.
     * @param width         The width of the order to specify in the blueprint.
     * @param length        The length of the order to specify in the blueprint.
     * @param height        The height of the order to specify in the blueprint.
     * @param roofing       The roofing of the order to specify in the blueprint.
     * @param slope         The slope of the order to specify in the blueprint.
     * @param rafterChoice  The choice of rafters on the order to specify in the blueprint.
     * @param active        Whether or not the order is currently active.
     * @param shedBlueprint The shed to include in the order. {@code null} for no shed.
     * @return The newly created order blueprint.
     */
    static OrderBlueprint from(int customer,
                               int width,
                               int length,
                               int height,
                               int roofing,
                               int slope,
                               RafterChoice rafterChoice,
                               boolean active,
                               ShedBlueprint shedBlueprint)
    {
        return new OrderRecord(-1, customer, null, width, length, height, roofing, null, slope, rafterChoice,
                shedBlueprint, null, null, active, -1, -1, null);
    }

    /**
     * Returns the id of the customer who placed the order.
     *
     * @return The customer who placed the order.
     */
    int getCustomerId();

    /**
     * Returns the width of the order.
     *
     * @return The width of the order.
     */
    int getWidth();

    /**
     * Sets the width of the order.
     *
     * @param width The new width.
     */
    void setWidth(int width);

    /**
     * Returns the length of the order.
     *
     * @return The length of the order.
     */
    int getLength();

    /**
     * Sets the length of the order.
     *
     * @param length The new length.
     */
    void setLength(int length);

    /**
     * Returns the height of the order.
     *
     * @return The height of the order.
     */
    int getHeight();

    /**
     * Sets the height of the order.
     *
     * @param height The new height.
     */
    void setHeight(int height);

    /**
     * Returns the id of the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    int getRoofingId();

    /**
     * Returns the slope of the roofing.
     *
     * @return The slope of the roofing.
     */
    int getSlope();

    /**
     * Sets the slope of the roofing.
     *
     * @param slope The new slope.
     */
    void setSlope(int slope);

    /**
     * Returns the choice of rafters made by the customer who placed the order.
     *
     * @return The choice of rafters made by the customer who placed the order.
     */
    RafterChoice getRafterChoice();

    /**
     * Returns the shed included in the order. {@code null} means no shed is included.
     *
     * @return The shed included in the order.
     */
    ShedBlueprint getShedBlueprint();
}
