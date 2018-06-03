package tvestergaard.fog.logic.construction.roofing;

import tvestergaard.fog.data.roofing.RoofingType;
import tvestergaard.fog.logic.construction.ComponentMap;
import tvestergaard.fog.logic.construction.ConstructionSpecification;
import tvestergaard.fog.logic.construction.skeleton.SkeletonConstructionSummary;

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
                                         ComponentMap components,
                                         SkeletonConstructionSummary skeletonConstructionSummary);
}
