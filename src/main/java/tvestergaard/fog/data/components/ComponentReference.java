package tvestergaard.fog.data.components;

public interface ComponentReference
{

    static ComponentReference from(int definition, int material)
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
