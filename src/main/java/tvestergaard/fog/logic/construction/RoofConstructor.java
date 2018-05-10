package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.roofing.RoofingType;

public interface RoofConstructor
{

    /**
     * Returns the type of roofing this object constructs.
     *
     * @return The type of roofing this object constructs.
     */
    RoofingType getType();

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the roofing must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    void construct(MutableConstructionSummary summary, ConstructionSpecification specification, Components components);
}
