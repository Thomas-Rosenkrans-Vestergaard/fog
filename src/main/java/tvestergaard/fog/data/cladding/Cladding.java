package tvestergaard.fog.data.cladding;

public interface Cladding extends CladdingUpdater
{

    /**
     * Checks that this cladding equals another provided object. The two objects are considered equal when all the
     * attributes of the instances are equal.
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
