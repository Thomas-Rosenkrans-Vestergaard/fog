package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Category;

public class ComponentDefinitionRecord implements ComponentDefinition
{

    private final int      id;
    private final String   identifier;
    private final String   notes;
    private final Category category;

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

    @Override public String getNotes()
    {
        return notes;
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
