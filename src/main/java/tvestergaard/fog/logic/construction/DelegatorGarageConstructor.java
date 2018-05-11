package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingType;

import java.util.HashMap;
import java.util.Map;

public class DelegatorGarageConstructor implements GarageConstructor
{


    /**
     * The object that constructs the skeleton of the garage to construct.
     */
    private final SkeletonConstructor skeletonConstructor;

    /**
     * The registered roof constructors mapped to the type of roof they construct.
     */
    private final Map<RoofingType, RoofConstructor> roofConstructors = new HashMap<>();

    /**
     * Creates a new {@link DelegatorGarageConstructor}.
     *
     * @param skeletonConstructor The object that constructs the skeleton of the garage to construct.
     */
    public DelegatorGarageConstructor(SkeletonConstructor skeletonConstructor)
    {
        this.skeletonConstructor = skeletonConstructor;
    }

    /**
     * Registers the provided roof constructor with the {@link DelegatorGarageConstructor}.
     *
     * @param constructor The roof constructor to register.
     */
    public void register(RoofConstructor constructor)
    {
        roofConstructors.put(constructor.getType(), constructor);
    }

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param specifications     The specifications for the garage to construct.
     * @param skeletonComponents The components used to construct the skeleton of the garage.
     * @param roofingComponents  The components used to construct the roofing of the garage.
     * @return The summary of the construction.
     */
    @Override public ConstructionSummary construct(ConstructionSpecification specifications,
                                                   Components skeletonComponents,
                                                   Components roofingComponents)
    {
        MutableConstructionSummary summary = new MutableConstructionSummary();
        constructSkeleton(summary, specifications, skeletonComponents);
        constructRoofing(summary, specifications, roofingComponents);

        return summary;
    }

    /**
     * Constructs the skeleton of the garage.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    private void constructSkeleton(MutableConstructionSummary summary,
                                   ConstructionSpecification specification,
                                   Components components)
    {
        skeletonConstructor.construct(summary, specification, components);
    }

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the roofing must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    private void constructRoofing(MutableConstructionSummary summary,
                                  ConstructionSpecification specification,
                                  Components components)
    {
        Roofing         roofing         = specification.getRoofing();
        RoofingType     roofingType     = roofing.getType();
        RoofConstructor roofConstructor = roofConstructors.get(roofingType);

        if (roofConstructor == null)
            throw new IllegalStateException("No constructor able to construct " + roofingType.name());

        roofConstructor.construct(summary, specification, components);
    }
}
