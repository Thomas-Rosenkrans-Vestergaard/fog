package tvestergaard.fog.logic.construction;

public interface GarageConstructionSummary
{

    /**
     * Returns the object containing information about the construction of the garage skeleton.
     *
     * @return The object containing information about the construction fo the garage skeleton.
     */
    SkeletonConstructionSummary getSkeletonConstructionSummary();

    /**
     * Returns the object containing information about the construction of the roofing.
     *
     * @return The object containing information about hte construction of the roofing.
     */
    RoofingConstructionSummary getRoofingConstructionSummary();

    /**
     * Returns the total cost of the construction.
     *
     * @return The total cost of the construction.
     */
    int getTotal();
}
