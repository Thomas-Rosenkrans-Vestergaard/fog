package tvestergaard.fog.data.materials;

public interface Material extends MaterialUpdater
{

    /**
     * Checks that this material equals another provided object. The two objects are considered equal when all the
     * attributes of the instances are equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the material.
     *
     * @return The id of the material.
     */
    int hashCode();
}
