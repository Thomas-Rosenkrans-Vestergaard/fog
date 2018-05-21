package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Material;

import java.util.Objects;

/**
 * The default implementation for the {@link Component} interface.
 */
public class ComponentRecord implements Component
{

    /**
     * The id of the definition of the component.
     */
    private final int definitionId;

    /**
     * The definition of the component.
     */
    private final ComponentDefinition definition;

    /**
     * The id of the material selected for the component.
     */
    private final int materialId;

    /**
     * The material selected for the component.
     */
    private final Material material;

    /**
     * Creates a new {@link ComponentRecord}.
     *
     * @param definitionId The id of the definition of the component.
     * @param definition   The definition of the component.
     * @param materialId   The id of the material selected for the component.
     * @param material     The material selected for the component.
     */
    public ComponentRecord(int definitionId, ComponentDefinition definition, int materialId, Material material)
    {
        this.definitionId = definitionId;
        this.definition = definition;
        this.materialId = materialId;
        this.material = material;
    }

    /**
     * Returns the id of the definition of the component.
     *
     * @return The id of the definition of the component.
     */
    @Override public int getDefinitionId()
    {
        return definitionId;
    }

    /**
     * Returns the id of the material selected as the component.
     *
     * @return The id of the material selected as the component.
     */
    @Override public int getMaterialId()
    {
        return materialId;
    }

    /**
     * Updates the notes of the component.
     *
     * @param notes The new notes.
     */
    @Override public void setNotes(String notes)
    {
        definition.setNotes(notes);
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
    @Override public Material getMaterial()
    {
        return material;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component that = (Component) o;
        return getDefinitionId() == that.getDefinitionId() &&
                getMaterialId() == that.getMaterialId() &&
                Objects.equals(getDefinition(), that.getDefinition()) &&
                Objects.equals(getMaterial(), that.getMaterial());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getDefinition(), getDefinitionId(), getMaterial(), getMaterialId());
    }

    @Override public String toString()
    {
        return "ComponentRecord{" +
                "definition=" + definition +
                ", definitionId=" + definitionId +
                ", material=" + material +
                ", materialId=" + materialId +
                '}';
    }
}
