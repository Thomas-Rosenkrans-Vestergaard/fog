package tvestergaard.fog.logic.construction;

public interface GarageConstructor
{

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param specifications     The specifications for the garage to construct.
     * @param skeletonComponents The components used to construct the skeleton of the garage.
     * @param roofingComponents  The components used to construct the roofing of the garage.
     * @return The summary of the construction.
     */
    GarageConstructionSummary construct(ConstructionSpecification specifications,
                                        Components skeletonComponents,
                                        Components roofingComponents);
}
