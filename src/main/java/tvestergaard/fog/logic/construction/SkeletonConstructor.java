package tvestergaard.fog.logic.construction;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications of the garage skeleton to construct.
     * @param components    The components to use while constructing the skeleton.
     * @return The object containing the results of the construction.
     */
    SkeletonConstructionSummary construct(ConstructionSpecification specification, ComponentMap components);
}
