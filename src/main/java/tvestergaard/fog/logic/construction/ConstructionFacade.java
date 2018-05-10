package tvestergaard.fog.logic.construction;

public class ConstructionFacade
{

    private final DelegatorGarageConstructor garageConstructor;

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
     * @return The bill of materials for the generated garage.
     */
    public MaterialList construct(ConstructionSpecification specifications, Components skeletonComponents, Components roofingComponents)
    {
        return garageConstructor.construct(specifications, skeletonComponents, roofingComponents);
    }
}
