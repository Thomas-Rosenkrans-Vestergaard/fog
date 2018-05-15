package tvestergaard.fog.logic.construction;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications of the garage skeleton to construct.
     * @param components    The components to use while constructing the skeleton.
     */
    SkeletonConstructionSummary construct(ConstructionSpecification specification, ComponentMap components);
}
