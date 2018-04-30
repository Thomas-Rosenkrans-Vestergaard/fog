package tvestergaard.fog.data.flooring;

public interface Flooring extends FlooringUpdater
{

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
