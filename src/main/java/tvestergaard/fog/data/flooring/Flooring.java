package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.cladding.Cladding;

public interface Flooring
{

    /**
     * Returns the unique identifier of the {@link Flooring}.
     *
     * @return The unique identifier of the {@link Flooring}.
     */
    int getId();

    /**
     * Returns the name of the {@link Flooring}.
     *
     * @return The name of the {@link Flooring}.
     */
    String getName();

    /**
     * Sets the name of the {@link Flooring}.
     *
     * @param name The updated name.
     */
    void setName(String name);

    /**
     * Returns the description of the {@link Flooring}.
     *
     * @return The description of the {@link Flooring}.
     */
    String getDescription();

    /**
     * Sets the description of the {@lilnk Flooring}.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns the price of the {@link Flooring} per square meter (in øre).
     *
     * @return the price of the {@link Flooring} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Sets the price per square meter of {@link Flooring}.
     *
     * @param price The new price.
     */
    void setPricePerSquareMeter(int price);

    /**
     * Checks that this {@link Flooring} equals another provided object. The two objects are considered equal when
     * both are an instance of {@link Flooring}, and their ids are the same.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the hashCode representing the {@link Flooring}. The hashCode should be the id of the {@link Flooring}.
     *
     * @return The hashCode representing the {@link Flooring}.
     */
    int hashCode();
}
