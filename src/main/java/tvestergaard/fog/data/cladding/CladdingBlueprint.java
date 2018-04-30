package tvestergaard.fog.data.cladding;

public interface CladdingBlueprint
{

    /**
     * Returns a new cladding blueprint from the provided information.
     *
     * @param name                The name of the cladding to specify in the blueprint.
     * @param description         The description of the cladding to specify in the blueprint.
     * @param pricePerSquareMeter The price of the cladding to specify in the blueprint.
     * @param active              Whether or not the cladding to specify in the blueprint can be applied to orders.
     * @return The newly created cladding blueprint.
     */
    static CladdingBlueprint from(String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new CladdingRecord(-1, name, description, pricePerSquareMeter, active);
    }

    /**
     * Returns the name of the cladding.
     *
     * @return The name of the cladding.
     */
    String getName();

    /**
     * Sets the name of the cladding.
     *
     * @param name The updated name.
     */
    void setName(String name);

    /**
     * Returns the description of the cladding.
     *
     * @return The description of the cladding.
     */
    String getDescription();

    /**
     * Sets the description of the {@lilnk Cladding}.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns the price of the cladding per square meter.
     *
     * @return the price of the cladding per square meter.
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price per square meter of cladding.
     *
     * @param price The new price.
     */
    void setPricePerSquareMeter(int price);

    /**
     * Returns {@code true} if the cladding can currently be applied to new orders.
     *
     * @return {@link true} if the cladding can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the cladding.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);
}
