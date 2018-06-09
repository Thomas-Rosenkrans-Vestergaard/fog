package tvestergaard.fog.logic.construction;

import tvestergaard.fog.logic.construction.roofing.RoofingConstructionSummary;
import tvestergaard.fog.logic.construction.skeleton.SkeletonConstructionSummary;

public class DefaultGarageConstructionSummary implements GarageConstructionSummary
{

    /**
     * The object containing information about the construction of the garage skeleton.
     */
    private final SkeletonConstructionSummary skeletonConstructionSummary;

    /**
     * The object containing information about the construction of the roofing.
     */
    private final RoofingConstructionSummary roofingConstructionSummary;

    /**
     * Creates a new {@link DefaultGarageConstructionSummary}.
     *
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @param roofingConstructionSummary  The object containing information about the construction of the roofing.
     */
    public DefaultGarageConstructionSummary(SkeletonConstructionSummary skeletonConstructionSummary,
                                            RoofingConstructionSummary roofingConstructionSummary)
    {
        this.skeletonConstructionSummary = skeletonConstructionSummary;
        this.roofingConstructionSummary = roofingConstructionSummary;
    }

    /**
     * Returns the object containing information about the construction of the garage skeleton.
     *
     * @return The object containing information about the construction fo the garage skeleton.
     */
    @Override public SkeletonConstructionSummary getSkeletonConstructionSummary()
    {
        return skeletonConstructionSummary;
    }

    /**
     * Returns the object containing information about the construction of the roofing.
     *
     * @return The object containing information about the construction of the roofing.
     */
    @Override public RoofingConstructionSummary getRoofingConstructionSummary()
    {
        return roofingConstructionSummary;
    }

    /**
     * Returns the total price of the construction.
     *
     * @return The total price of the construction.
     */
    @Override public int getTotal()
    {
        return roofingConstructionSummary.getTotal() + skeletonConstructionSummary.getTotal();
    }
}
