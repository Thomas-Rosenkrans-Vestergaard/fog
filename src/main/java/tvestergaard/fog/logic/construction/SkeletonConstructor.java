package tvestergaard.fog.logic.construction;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    SkeletonConstructionSummary construct(ConstructionSpecification specification, Components components);
}
