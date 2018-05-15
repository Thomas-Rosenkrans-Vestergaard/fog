package tvestergaard.fog.logic.construction;

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
     * The total cost of the construction.
     */
    private int total = 0;

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

        this.total += skeletonConstructionSummary.getTotal();
        this.total += skeletonConstructionSummary.getTotal();
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
        return total;
    }
}
