package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.Category;

public interface RoofingComponentDefinition
{

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    int getId();

    /**
     * Returns the roofing type the component is defined for.
     *
     * @return The roofing type the component is defined for.
     */
    RoofingType getRoofingType();

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    String getIdentifier();

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    Category getCategory();
}
