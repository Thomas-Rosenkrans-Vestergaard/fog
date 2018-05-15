package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Category;

public interface ComponentDefinition
{

    /**
     * Returns the id of the component definition.
     *
     * @return The id of the component definition.
     */
    int getId();

    /**
     * Returns the identifier of the component.
     *
     * @return The identifier of the component.
     */
    String getIdentifier();

    /**
     * Returns the notes of the component.
     *
     * @return The notes of the component.
     */
    String getNotes();

    /**
     * Updates the notes of the component.
     *
     * @param notes The new notes.
     */
    void setNotes(String notes);

    /**
     * Returns the material category that the component must be of.
     *
     * @return The material category that the component must be of.
     */
    Category getCategory();
}
