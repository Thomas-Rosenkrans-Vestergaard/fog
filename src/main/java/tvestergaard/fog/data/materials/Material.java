package tvestergaard.fog.data.materials;

public interface Material extends MaterialUpdater
{

    /**
     * Creates a new material blueprint using the provided information.
     *
     * @param number      The material number to specify in the blueprint.
     * @param description The material description to specify in the blueprint.
     * @param notes       The notes on the material to specify in the blueprint.
     * @param height      The height of the material to specify in the blueprint.
     * @param width       The width of the material to specify in the blueprint.
     * @param usage       The usage of the material to specify in the blueprint.
     * @return The resulting blueprint.
     */
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
