package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.flooring.Flooring;

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
     * Returns {@code true} if the {@link Roofing} can currently be applied to new orders.
     *
     * @return {@link true} if the {@link Roofing} can currently be applied to new orders.
     */
    boolean isActive();

    /**
     * Sets the active status of the {@link Roofing}.
     *
     * @param active The new active status.
     */
    void setActive(boolean active);

    /**
     * Checks that this {@link Roofing} equals another provided object. The two objects are only considered equal when
     * all the attributes of the two {@link Roofing}s are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the {@link Roofing}.
     *
     * @return The id of the {@link Roofing}.
     */
    int hashCode();
}
