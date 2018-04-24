package tvestergaard.fog.data.roofing;

public interface Roofing
{

    /**
     * Returns the unique identifier of the {@link Roofing}.
     *
     * @return The unique identifier of the {@link Roofing}.
     */
    int getId();

    /**
     * Returns the name of the {@link Roofing}.
     *
     * @return The name of the {@link Roofing}.
     */
    String getName();

    /**
     * Sets the name of the {@link Roofing}.
     *
     * @param name The new name.
     */
    void setName(String name);

    /**
     * Returns the description of the {@link Roofing}.
     *
     * @return The description of the {@link Roofing}.
     */
    String getDescription();

    /**
     * Sets the description of the {@link Roofing}.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns the minimum slope the {@link Roofing} must have.
     *
     * @return The minimum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMinimumSlope();

    /**
     * Sets the minimum slope the {@link Roofing} must have.
     *
     * @param minimumSlope The new minimum slope. Range between 0 and 90 (exclusive).
     */
    void setMinimumSlope(int minimumSlope);

    /**
     * Returns the maximum slope the {@link Roofing} can have.
     *
     * @return The maximum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMaximumSlope();

    /**
     * Sets the maximum slope the {@link Roofing} can have.
     *
     * @param maximumSlope The new maximum slope. Range between 0 and 90 (exclusive).
     */
    void setMaximumSlope(int maximumSlope);

    /**
     * Returns the price of the {@link Roofing} per square meter (in øre).
     *
     * @return The price of the {@link Roofing} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price of the {@link Roofing} per square meter.
     *
     * @param price The new price (in øre).
     */
    void setPricePerSquareMeter(int price);

    /**
     * Checks that this {@link Roofing} equals another provided object. The two objects are considered equal when
     * both are an instance of {@link Roofing}, and their ids are the same.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    abstract boolean equals(Object other);

    /**
     * Returns the hashCode representing the {@link Roofing}. The hashCode should be the id of the {@link Roofing}.
     *
     * @return The hashCode representing the {@link Roofing}.
     */
    abstract int hashCode();
}
