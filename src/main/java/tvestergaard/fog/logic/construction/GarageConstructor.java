package tvestergaard.fog.logic.construction;

public interface GarageConstructor
{

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param specifications     The specifications for the garage to construct.
     * @param skeletonComponents The components used to construct the skeleton of the garage.
     * @param roofingComponents  The components used to construct the roofing of the garage.
     * @return The bill of materials for the generated garage.
     */
    MaterialList construct(ConstructionSpecification specifications,
                           Components skeletonComponents,
                           Components roofingComponents);
}
