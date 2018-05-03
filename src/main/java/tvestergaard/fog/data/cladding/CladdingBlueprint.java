package tvestergaard.fog.data.cladding;

/**
 * Contains the information necessary to create a new cladding.
 */
public interface CladdingBlueprint
{

    /**
     * Returns a new cladding blueprint from the provided information.
     *
     * @param name        The name of the cladding to specify in the blueprint.
     * @param description The description of the cladding to specify in the blueprint.
     * @param active      Whether or not the cladding to specify in the blueprint can be applied to orders.
     * @return The newly created cladding blueprint.
     */
    static CladdingBlueprint from(String name, String description, boolean active)
    {
        return new CladdingRecord(-1, name, description, active);
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
