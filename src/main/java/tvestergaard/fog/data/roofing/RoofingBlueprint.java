package tvestergaard.fog.data.roofing;

public interface RoofingBlueprint
{

    /**
     * Returns the name of the roofing.
     *
     * @return The name of the roofing.
     */
    String getName();

    /**
     * Sets the name of the roofing.
     *
     * @param name The new name.
     */
    void setName(String name);

    /**
     * Returns the description of the roofing.
     *
     * @return The description of the roofing.
     */
    String getDescription();

    /**
     * Sets the description of the roofing.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns the minimum slope the roofing must have.
     *
     * @return The minimum slope the roofing must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMinimumSlope();

    /**
     * Sets the minimum slope the roofing must have.
     *
     * @param minimumSlope The new minimum slope. Range between 0 and 90 (exclusive).
     */
    void setMinimumSlope(int minimumSlope);

    /**
     * Returns the maximum slope the roofing can have.
     *
     * @return The maximum slope the roofing must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMaximumSlope();

    /**
     * Sets the maximum slope the roofing can have.
     *
     * @param maximumSlope The new maximum slope. Range between 0 and 90 (exclusive).
     */
    void setMaximumSlope(int maximumSlope);

    /**
     * Returns the price of the roofing per square meter (in øre).
     *
     * @return The price of the roofing per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price of the roofing per square meter.
     *
     * @param price The new price (in øre).
     */
    void setPricePerSquareMeter(int price);

    /**
     * Returns {@code true} if the roofing can currently be applied to new orders.
     *
     * @return {@link true} if the roofing can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the roofing.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);
}
