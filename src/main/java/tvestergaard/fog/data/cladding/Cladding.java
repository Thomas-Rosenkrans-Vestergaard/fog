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
     * Checks that this {@link Cladding} equals another provided object. The two objects are considered equal when
     * both are an instance of {@link Cladding}, and their ids are the same.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    abstract boolean equals(Object other);

    /**
     * Returns the hashCode representing the {@link Cladding}. The hashCode should be the id of the {@link Cladding}.
     *
     * @return The hashCode representing the {@link Cladding}.
     */
    abstract int hashCode();
}
