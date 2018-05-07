package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.SimpleMaterial;

public interface ComponentValue extends ComponentValueReference
{

    /**
     * Returns the component definition the value is assigned to.
     *
     * @return The component definition the value is assigned to.
     */
    ComponentDefinition getDefinition();

    /**
     * Returns the material assigned to the component definition.
     *
     * @return The material assigned to the component definition.
     */
    SimpleMaterial getMaterial();
}
