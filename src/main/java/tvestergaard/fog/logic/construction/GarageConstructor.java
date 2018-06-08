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
     * @throws ConstructionException When an exception occurs while constructing the order.
     */
    GarageConstructionSummary construct(ConstructionSpecification specifications,
                                        ComponentMap skeletonComponents,
                                        ComponentMap roofingComponents) throws ConstructionException;
}
