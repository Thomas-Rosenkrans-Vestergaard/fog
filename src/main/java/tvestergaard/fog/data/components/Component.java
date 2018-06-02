package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Category;
import tvestergaard.fog.data.materials.Material;

public interface Component extends ComponentConnection, ComponentDefinition
{

    /**
     * Returns the component definition the value is assigned to.
     *
     * @return The component definition the value is assigned to.
     */
    ComponentDefinition getDefinition();

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    default int getId()
    {
        return getDefinition().getId();
    }

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    default String getIdentifier()
    {
        return getDefinition().getIdentifier();
    }

    /**
     * Returns the notes of the roofing component.
     *
     * @return The notes of the roofing component.
     */
    default String getNotes()
    {
        return getDefinition().getNotes();
    }

    /**
     * Returns id of the material category that the component must be of.
     *
     * @return The id of the material category that the component must be of.
     */
    @Override default int getCategoryId()
    {
        return getDefinition().getCategoryId();
    }

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    default Category getCategory()
    {
        return getDefinition().getCategory();
    }

    /**
     * Returns the material assigned to the component definition.
     *
     * @return The material assigned to the component definition.
     */
    Material getMaterial();
}
