package tvestergaard.fog.data.flooring;

public interface Flooring
{

    /**
     * Returns the unique identifier of the flooring.
     *
     * @return The unique identifier of the flooring.
     */
    int getId();

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
     * Returns the price of the flooring per square meter (in øre).
     *
     * @return the price of the flooring per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price per square meter of flooring.
     *
     * @param price The new price.
     */
    void setPricePerSquareMeter(int price);

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

    /**
     * Checks that this flooring equals another provided object. The two objects are only considered equal when all the
     * attributes of the two floorings are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the flooring.
     *
     * @return The id of the flooring.
     */
    int hashCode();
}
