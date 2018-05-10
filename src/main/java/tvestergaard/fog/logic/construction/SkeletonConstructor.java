package tvestergaard.fog.logic.construction;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param materials     The summary being updated with the materials needed to construct the skeleton.
     * @param components    The components to use while constructing the skeleton.
     * @param specification The specifications that the skeleton must satisfy.
     */
    void construct(MutableMaterialList materials, Components components, ConstructionSpecification specification);
}
