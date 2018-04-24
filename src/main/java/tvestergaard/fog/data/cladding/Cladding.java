package tvestergaard.fog.data.cladding;

public interface Cladding
{

    /**
     * Returns the unique identifier of the {@link Cladding}.
     *
     * @return The unique identifier of the {@link Cladding}.
     */
    int getId();

    /**
     * Returns the name of the {@link Cladding}.
     *
     * @return The name of the {@link Cladding}.
     */
    String getName();

    /**
     * Sets the name of the {@link Cladding}.
     *
     * @param name The updated name.
     */
    void setName(String name);

    /**
     * Returns the description of the {@link Cladding}.
     *
     * @return The description of the {@link Cladding}.
     */
    String getDescription();

    /**
     * Sets the description of the {@lilnk Cladding}.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns the price of the {@link Cladding} per square meter (in øre).
     *
     * @return the price of the {@link Cladding} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price per square meter of {@link Cladding}.
     *
     * @param price The new price.
     */
    void setPricePerSquareMeter(int price);

    /**
     * Returns {@code true} if the {@link Cladding} can currently be applied to new orders.
     *
     * @return {@link true} if the {@link Cladding} can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the {@link Cladding}.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);

    /**
     * Checks that this {@link Cladding} equals another provided object. The two objects are only considered equal when
     * all the attributes of the two {@link Cladding}s are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the {@link Cladding}.
     *
     * @return The id of the {@link Cladding}.
     */
    int hashCode();
}
