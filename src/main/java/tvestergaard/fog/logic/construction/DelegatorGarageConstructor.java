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
    private final Map<RoofingType, RoofingConstructor> roofConstructors = new HashMap<>();

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
    public void register(RoofingConstructor constructor)
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
    @Override public GarageConstructionSummary construct(ConstructionSpecification specifications,
                                                         Components skeletonComponents,
                                                         Components roofingComponents)
    {
        SkeletonConstructionSummary skeletonConstructionSummary = constructSkeleton(specifications, skeletonComponents);
        return new DefaultGarageConstructionSummary(
                skeletonConstructionSummary,
                constructRoofing(specifications, roofingComponents, skeletonConstructionSummary));
    }

    /**
     * Constructs the skeleton of the garage.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     * @return The object containing information about the construction of the skeleton.
     */
    private SkeletonConstructionSummary constructSkeleton(ConstructionSpecification specification, Components components)
    {
        return skeletonConstructor.construct(specification, components);
    }

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param specification               The specifications that the roofing must satisfy.
     * @param components                  The components to use while constructing the skeleton.
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @return The object containing information about the construction of the roofing of the garage.
     */
    private RoofingConstructionSummary constructRoofing(ConstructionSpecification specification,
                                                        Components components,
                                                        SkeletonConstructionSummary skeletonConstructionSummary)
    {
        Roofing            roofing         = specification.getRoofing();
        RoofingType        roofingType     = roofing.getType();
        RoofingConstructor roofConstructor = roofConstructors.get(roofingType);

        if (roofConstructor == null)
            throw new IllegalStateException("No constructor able to construct " + roofingType.name());

        return roofConstructor.construct(specification, components, skeletonConstructionSummary);
    }
}
