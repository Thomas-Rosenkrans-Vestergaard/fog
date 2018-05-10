package tvestergaard.fog.logic.construction;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    void construct(MutableConstructionSummary summary, ConstructionSpecification specification, Components components);
}
