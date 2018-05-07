package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.Category;

public interface ComponentDefinition
{

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    int getId();

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    String getIdentifier();

    /**
     * Returns the notes of the roofing component.
     *
     * @return The notes of the roofing component.
     */
    String getNotes();

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    Category getCategory();
}
