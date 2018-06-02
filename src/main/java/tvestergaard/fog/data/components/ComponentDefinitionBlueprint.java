package tvestergaard.fog.data.components;

public interface ComponentDefinitionBlueprint
{

    /**
     * Creates a new {@link ComponentDefinitionBlueprint}.
     *
     * @param identifier The identifier of the component definition to create.
     * @param notes      The notes on the component definition to create.
     * @param category   The id of the category.
     * @return The resulting component definition blueprint.
     */
    public static ComponentDefinitionBlueprint from(String identifier, String notes, int category)
    {
        return new ComponentDefinitionRecord(-1, identifier, notes, category, null);
    }

    /**
     * Returns the identifier of the component.
     *
     * @return The identifier of the component.
     */
    String getIdentifier();

    /**
     * Returns id of the material category that the component must be of.
     *
     * @return The id of the material category that the component must be of.
     */
    int getCategoryId();

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
}
