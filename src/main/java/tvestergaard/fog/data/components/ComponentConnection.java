package tvestergaard.fog.data.components;

public interface ComponentConnection
{

    /**
     * Creates a new component blueprint from the provided definition id and material id.
     *
     * @param definition The definition of the blueprint.
     * @param material   The material of the blueprint.
     * @return The resulting Component blueprint.
     */
    static ComponentConnection from(int definition, int material)
    {
        return new ComponentRecord(definition, null, material, null);
    }

    /**
     * Returns the id of the definition of the component.
     *
     * @return The id of the definition of the component.
     */
    int getDefinitionId();

    /**
     * Returns the id of the material selected as the component.
     *
     * @return The id of the material selected as the component.
     */
    int getMaterialId();
}
