package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.roofing.RoofingType;

public interface RoofConstructor
{

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param materials     The summary being updated with the materials needed to construct the roof.
     * @param components    The components to use while constructing the roof.
     * @param specification The specifications that the roof must satisfy.
     */
    void construct(MutableMaterialList materials, Components components, ConstructionSpecification specification);

    /**
     * Returns the type of roofing this object constructs.
     *
     * @return The type of roofing this object constructs.
     */
    RoofingType getType();
}
