package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.categories.Category;

/**
 * Represents some component definition.
 */
public interface ComponentDefinition extends ComponentDefinitionBlueprint
{

    /**
     * Returns the id of the component definition.
     *
     * @return The id of the component definition.
     */
    int getId();

    /**
     * Returns the material category that the component must be of.
     *
     * @return The material category that the component must be of.
     */
    Category getCategory();
}
