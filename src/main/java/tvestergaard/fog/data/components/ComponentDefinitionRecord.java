package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Category;

public class ComponentDefinitionRecord implements ComponentDefinition
{

    /**
     * The id of the component definition.
     */
    private final int id;

    /**
     * The identifier of the component definition.
     */
    private final String identifier;

    /**
     * The notes of the component definition.
     */
    private String notes;

    /**
     * The category the component definition expects the material to be a part of.
     */
    private final Category category;

    /**
     * Creates a new {@link ComponentDefinitionRecord}.
     *
     * @param id         The id of the component definition.
     * @param identifier The identifier of the component definition.
     * @param notes      The notes of the component definition.
     * @param category   The category the component definition expects the material to be a part of.
     */
    public ComponentDefinitionRecord(int id, String identifier, String notes, Category category)
    {
        this.id = id;
        this.identifier = identifier;
        this.notes = notes;
        this.category = category;
    }

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    @Override public String getIdentifier()
    {
        return identifier;
    }

    /**
     * Returns the notes of the component.
     *
     * @return The notes of the component.
     */
    @Override public String getNotes()
    {
        return notes;
    }

    /**
     * Updates the notes of the component.
     *
     * @param notes The new notes.
     */
    @Override public void setNotes(String notes)
    {
        this.notes = notes;
    }

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    @Override public Category getCategory()
    {
        return category;
    }
}
