package tvestergaard.fog.data.flooring;

public interface FlooringBlueprint
{

    /**
     * Returns a new flooring blueprint from the provided information.
     *
     * @param name                The name of the flooring to specify in the blueprint.
     * @param description         The description of the flooring to specify in the blueprint.
     * @param active              Whether or not the flooring to specify in the blueprint can be applied to orders.
     * @return The newly created flooring blueprint.
     */
    static FlooringBlueprint from(String name, String description, boolean active)
    {
        return new FlooringRecord(-1, name, description, active);
    }

    /**
     * Returns the name of the flooring.
     *
     * @return The name of the flooring.
     */
    String getName();

    /**
     * Sets the name of the flooring.
     *
     * @param name The updated name.
     */
    void setName(String name);

    /**
     * Returns the description of the flooring.
     *
     * @return The description of the flooring.
     */
    String getDescription();

    /**
     * Sets the description of the {@lilnk Flooring}.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns {@code true} if the flooring can currently be applied to new orders.
     *
     * @return {@link true} if the flooring can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the flooring.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);
}
