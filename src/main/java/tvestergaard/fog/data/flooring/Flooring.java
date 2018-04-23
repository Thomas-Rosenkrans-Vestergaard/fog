package tvestergaard.fog.data.flooring;

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
     * Returns the description of the {@link Flooring}.
     *
     * @return The description of the {@link Flooring}.
     */
    String getDescription();

    /**
     * Returns the price of the {@link Flooring} per square meter (in øre).
     *
     * @return the price of the {@link Flooring} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    /**
     * Checks that this {@link Flooring} equals another provided object. The two objects are considered equal when
     * both are an instance of {@link Flooring}, and their ids are the same.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    abstract boolean equals(Object other);

    /**
     * Returns the hashCode representing the {@link Flooring}. The hashCode should be the id of the {@link Flooring}.
     *
     * @return The hashCode representing the {@link Flooring}.
     */
    abstract int hashCode();
}
