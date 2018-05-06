package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

public interface RoofRidgeLathHolder extends Material
{

    /**
     * Returns the interval that the ridge lath holder should be mounted at (millimeters).
     *
     * @return the interval that the ridge lath holder should be mounted at (millimeters).
     */
    default int getPlacementInverval()
    {
        return getAttribute("PLACEMENT_INTERVAL_MM").getInt();
    }
}
