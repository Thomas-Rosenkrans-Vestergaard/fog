package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.roofing.RoofingType;

public interface RoofingConstructor
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
     * @param specification               The specifications that the roofing must satisfy.
     * @param components                  The components to use while constructing the roofing.
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @return The summary containing information about the construction of the roofing.
     */
    RoofingConstructionSummary construct(ConstructionSpecification specification,
                                         Components components,
                                         SkeletonConstructionSummary skeletonConstructionSummary);
}
