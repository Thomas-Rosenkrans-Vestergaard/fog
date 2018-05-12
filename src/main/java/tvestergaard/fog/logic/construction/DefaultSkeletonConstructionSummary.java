package tvestergaard.fog.logic.construction;

public class DefaultSkeletonConstructionSummary implements SkeletonConstructionSummary
{

    /**
     * The materials needed to construct the roofing.
     */
    private final Materials materials;

    /**
     * The drawing of the aerial view of the skeleton construction.
     */
    private final ConstructionDrawing aerialView;

    /**
     * The drawing of the side view of the skeleton construction.
     */
    private final ConstructionDrawing sideView;

    /**
     * Creates a new {@link DefaultSkeletonConstructionSummary}.
     *
     * @param materials The materials needed to construct the roofing.
     * @param aerialView The drawing of the aerial view of the skeleton construction.
     * @param sideView  The drawing of the side view of the skeleton construction.
     */
    public DefaultSkeletonConstructionSummary(Materials materials, ConstructionDrawing aerialView, ConstructionDrawing sideView)
    {
        this.materials = materials;
        this.aerialView = aerialView;
        this.sideView = sideView;
    }

    /**
     * Returns the materials needed to construct the roofing.
     *
     * @return The materials needed to construct the roofing.
     */
    @Override public Materials getMaterials()
    {
        return materials;
    }

    /**
     * Returns the drawing of the aerial view of the skeleton construction.
     *
     * @return The drawing of the aerial view of the skeleton construction.
     */
    @Override public ConstructionDrawing getAerialView()
    {
        return aerialView;
    }

    /**
     * Returns the drawing of the side view of the skeleton construction.
     *
     * @return The drawing of the side view of the skeleton construction.
     */
    @Override public ConstructionDrawing getSideView()
    {
        return sideView;
    }
}
