package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.Material;

public interface RoofingComponentValue
{

    /**
     * Returns the component definition the value is assigned to.
     *
     * @return The component definition the value is assigned to.
     */
    RoofingComponentDefinition getDefinition();

    /**
     * Returns the material assigned to the component definition.
     * @return The material assigned to the component definition.
     */
    Material getMaterial();

    /**
     * Returns the notes on the component
     * @return
     */
    String getNotes();
}
