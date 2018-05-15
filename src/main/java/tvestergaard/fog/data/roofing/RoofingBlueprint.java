package tvestergaard.fog.data.roofing;

public interface RoofingBlueprint
{

    /**
     * Returns a new roofing blueprint from the provided information.
     *
     * @param name        The name of the roofing to specify in the blueprint.
     * @param description The description of the roofing to specify in the blueprint.
     * @param active      Whether or not the roofing to specify in the blueprint can be applied to orders.
     * @param type        The type of roofing.
     * @return The newly created roofing blueprint.
     */
    static RoofingBlueprint from(String name, String description, boolean active, RoofingType type)
    {
        return new RoofingRecord(-1, name, description, active, type);
    }

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
     * Returns {@code true} if the roofing can currently be applied to new orders.
     *
     * @return {@code true} if the roofing can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the roofing.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);

    /**
     * Returns the type of the roofing.
     *
     * @return The type of the roofing.
     */
    RoofingType getType();

    /**
     * Sets the type of the roofing.
     *
     * @param type The new roofing type.
     */
    void setType(RoofingType type);
}
