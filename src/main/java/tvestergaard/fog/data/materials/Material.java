package tvestergaard.fog.data.materials;

public interface Material extends MaterialUpdater
{

    static MaterialBlueprint blueprint(String number, String description, String notes, int height, int width, int usage)
    {
        return new MaterialRecord(-1, number, description, notes, height, width, usage);
    }

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
