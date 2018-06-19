package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Material;

import java.util.List;
import java.util.Objects;

/**
 * The default implementation for the {@link Component} interface.
 */
public class ComponentRecord implements Component
{

    /**
     * The unique id identifying the value.
     */
    private final int id;

    /**
     * The id of the definition of the component.
     */
    private final int definitionId;

    /**
     * The definition of the component.
     */
    private final ComponentDefinition definition;

    /**
     * The materials selected for the component.
     */
    private final List<Material> materials;

    /**
     * Creates a new {@link ComponentRecord}.
     *
     * @param id           The unique id identifying the value.
     * @param definitionId The id of the definition of the component.
     * @param definition   The definition of the component.
     * @param materials    The materials selected for the component.
     */
    public ComponentRecord(int id, int definitionId, ComponentDefinition definition, List<Material> materials)
    {
        this.id = id;
        this.definitionId = definitionId;
        this.definition = definition;
        this.materials = materials;
    }

    /**
     * Returns the unique id identifying the value.
     *
     * @return The unique id identifying the value.
     */
    @Override public int getId()
    {
        return id;
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
        return getMaterial().getId();
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
     * Returns the materials assigned to the component.
     *
     * @return The materials assigned to the component. When the component contains only one materials, this method
     * returns {@code null}.
     */
    @Override public List<Material> getMaterials()
    {
        return materials;
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
        return "ComponentValueRecord{" +
                "id=" + id +
                ", definitionId=" + definitionId +
                ", definition=" + definition +
                ", materials=" + materials +
                '}';
    }
}
