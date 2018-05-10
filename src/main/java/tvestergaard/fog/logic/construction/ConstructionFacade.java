package tvestergaard.fog.logic.construction;

public class ConstructionFacade
{

    /**
     * The garage constructor used to construct garages.
     */
    private final DelegatorGarageConstructor garageConstructor;

    /**
     * Creates a new {@link ConstructionFacade}.
     */
    public ConstructionFacade()
    {
        SkeletonConstructor skeletonConstructor = new CAR01SkeletonConstructor();
        RoofConstructor     roofConstructor     = new TiledRoofConstructor();
        garageConstructor = new DelegatorGarageConstructor(skeletonConstructor);
        garageConstructor.register(roofConstructor);
    }

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param specifications     The specifications for the garage to construct.
     * @param skeletonComponents The components used to construct the skeleton of the garage.
     * @param roofingComponents  The components used to construct the roofing of the garage.
     * @return The summary of the construction.
     */
    public ConstructionSummary construct(ConstructionSpecification specifications,
                                         Components skeletonComponents,
                                         Components roofingComponents)
    {
        return garageConstructor.construct(specifications, skeletonComponents, roofingComponents);
    }
}
