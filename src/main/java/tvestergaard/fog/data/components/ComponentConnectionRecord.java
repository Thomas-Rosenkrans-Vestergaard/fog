package tvestergaard.fog.data.components;

/**
 * The default implementation of the {@link ComponentConnection} interface.
 */
public class ComponentConnectionRecord implements ComponentConnection
{

    /**
     * The id of the component definition.
     */
    private final int definition;

    /**
     * The id of the material.
     */
    private final int material;

    /**
     * Creates a new {@link ComponentConnectionRecord}.
     *
     * @param definition The id of the component definition.
     * @param material   The id of the material.
     */
    public ComponentConnectionRecord(int id, int definition, int material)
    {
        this.definition = definition;
        this.material = material;
    }

    /**
     * Returns the id of the definition of the component.
     *
     * @return The id of the definition of the component.
     */
    @Override public int getDefinitionId()
    {
        return definition;
    }

    /**
     * Returns the id of the material selected as the component.
     *
     * @return The id of the material selected as the component.
     */
    @Override public int getMaterialId()
    {
        return material;
    }
}
