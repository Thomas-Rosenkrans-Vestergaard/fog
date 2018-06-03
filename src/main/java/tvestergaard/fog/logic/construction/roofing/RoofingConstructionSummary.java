package tvestergaard.fog.logic.construction.roofing;

import tvestergaard.fog.logic.construction.ConstructionDrawing;
import tvestergaard.fog.logic.construction.Materials;

public interface RoofingConstructionSummary
{

    /**
     * Returns the materials needed to construct the roofing.
     *
     * @return The materials needed to construct the roofing.
     */
    Materials getMaterials();

    /**
     * Returns the drawing of the aerial view of the roofing skeleton.
     *
     * @return The drawing of the aerial view of the roofing skeleton.
     */
    ConstructionDrawing getAerialSkeletonView();

    /**
     * Returns the drawing of the aerial view of the tiled roofing.
     *
     * @return The drawing of the aerial view of the tiled roofing.
     */
    ConstructionDrawing getAerialTiledView();

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
