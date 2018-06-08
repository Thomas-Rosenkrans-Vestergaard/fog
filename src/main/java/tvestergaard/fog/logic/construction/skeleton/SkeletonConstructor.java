package tvestergaard.fog.logic.construction.skeleton;

import tvestergaard.fog.logic.construction.ComponentMap;
import tvestergaard.fog.logic.construction.ConstructionException;
import tvestergaard.fog.logic.construction.ConstructionSpecification;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications of the garage skeleton to construct.
     * @param components    The components to use while constructing the skeleton.
     * @return The object containing the results of the construction.
     * @throws ConstructionException When an exception occurs while constructing the order.
     */
    SkeletonConstructionSummary construct(ConstructionSpecification specification, ComponentMap components)
            throws ConstructionException;
}
