package tvestergaard.fog.data.roofing;

public interface Roofing extends RoofingUpdater
{

    /**
     * Checks that this roofing equals another provided object. The two objects are only considered equal when all the
     * attributes of the two roofings are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the roofing.
     *
     * @return The id of the roofing.
     */
    int hashCode();
}
