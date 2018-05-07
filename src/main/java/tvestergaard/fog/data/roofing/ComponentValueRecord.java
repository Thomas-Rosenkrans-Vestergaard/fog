package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.SimpleMaterial;

public class ComponentValueRecord implements ComponentValue
{

    private final ComponentDefinition definition;
    private final int                 definitionId;
    private final SimpleMaterial      material;
    private final int                 materialId;

    public ComponentValueRecord(int definitionId, ComponentDefinition definition, int materialId, SimpleMaterial material)
    {
        this.definitionId = definitionId;
        this.definition = definition;
        this.materialId = materialId;
        this.material = material;
    }

    @Override public int getDefinitionId()
    {
        return definitionId;
    }

    @Override public int getMaterialId()
    {
        return materialId;
    }

    /**
     * Returns the component definition the value is assigned to.
     *
     * @return The component definition the value is assigned to.
     */
    @Override public ComponentDefinition getDefinition()
    {
        return definition;
    }

    /**
     * Returns the material assigned to the component definition.
     *
     * @return The material assigned to the component definition.
     */
    @Override public SimpleMaterial getMaterial()
    {
        return material;
    }
}
