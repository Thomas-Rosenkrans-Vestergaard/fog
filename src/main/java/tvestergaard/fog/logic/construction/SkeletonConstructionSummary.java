package tvestergaard.fog.logic.construction;

public interface SkeletonConstructionSummary
{

    /**
     * Returns the materials needed to construct the roofing.
     *
     * @return The materials needed to construct the roofing.
     */
    Materials getMaterials();

    /**
     * Returns the drawing of the aerial view of the skeleton construction.
     *
     * @return The drawing of the aerial view of the skeleton construction.
     */
    ConstructionDrawing getAerialView();

    /**
     * Returns the drawing of the side view of the skeleton construction.
     *
     * @return The drawing of the side view of the skeleton construction.
     */
    ConstructionDrawing getSideView();

    /**
     * Returns the total price of the construction.
     *
     * @return The total price of the construction.
     */
    default int getTotal()
    {
        return getMaterials().getTotal();
    }
}
