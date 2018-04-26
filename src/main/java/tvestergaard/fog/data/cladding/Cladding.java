package tvestergaard.fog.data.cladding;

public interface Cladding
{

    /**
     * Returns the unique identifier of the cladding.
     *
     * @return The unique identifier of the cladding.
     */
    int getId();

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
     * Returns the price of the cladding per square meter (in øre).
     *
     * @return the price of the cladding per square meter (in øre).
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

    /**
     * Checks that this cladding equals another provided object. The two objects are only considered equal when all the
     * attributes of the two claddings are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the cladding.
     *
     * @return The id of the cladding.
     */
    int hashCode();
}
