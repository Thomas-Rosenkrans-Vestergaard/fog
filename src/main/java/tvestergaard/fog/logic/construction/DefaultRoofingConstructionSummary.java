package tvestergaard.fog.logic.construction;

public class DefaultRoofingConstructionSummary implements RoofingConstructionSummary
{

    /**
     * The materials needed to construct the roofing.
     */
    private final Materials materials;

    /**
     * The drawing of the aerial view of the roofing skeleton.
     */
    private final ConstructionDrawing aerialSkeletonView;

    /**
     * The drawing of the aerial view of the tiled roofing.
     */
    private final ConstructionDrawing aerialTiledView;

    /**
     * The drawing of the roofing from the perspective of the gable.
     */
    private final ConstructionDrawing gableView;

    /**
     * Creates a new {@link DefaultRoofingConstructionSummary}.
     *
     * @param materials          The materials needed to construct the roofing.
     * @param aerialSkeletonView The drawing of the aerial view of the roofing skeleton.
     * @param aerialTiledView    The drawing of the aerial view of the tiled roofing.
     * @param gableView          The drawing of the roofing from the perspective of the gable.
     */
    public DefaultRoofingConstructionSummary(Materials materials,
                                             ConstructionDrawing aerialSkeletonView,
                                             ConstructionDrawing aerialTiledView,
                                             ConstructionDrawing gableView)
    {
        this.materials = materials;
        this.aerialSkeletonView = aerialSkeletonView;
        this.aerialTiledView = aerialTiledView;
        this.gableView = gableView;
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
     * Returns the drawing of the aerial view of the roofing skeleton.
     *
     * @return The drawing of the aerial view of the roofing skeleton.
     */
    @Override public ConstructionDrawing getAerialSkeletonView()
    {
        return aerialSkeletonView;
    }

    /**
     * Returns the drawing of the aerial view of the tiled roofing.
     *
     * @return The drawing of the aerial view of the tiled roofing.
     */
    @Override public ConstructionDrawing getAerialTiledView()
    {
        return aerialTiledView;
    }

    /**
     * Returns the drawing of the roofing from the perspective of the gable.
     *
     * @return The drawing of the roofing from the perspective of the gable.
     */
    @Override public ConstructionDrawing getGableView()
    {
        return gableView;
    }
}
