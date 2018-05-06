package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

public interface RoofTile extends Material
{

    /**
     * Returns the width of the roof tile in millimeters.
     *
     * @return The width of the roof tile in millimeters.
     */
    default int getWidth()
    {
        return getAttribute("WIDTH_MM").getInt();
    }

    /**
     * Returns the height of the roof tile in millimeters.
     *
     * @return The height of the roof tile in millimeters.
     */
    default int getHeight()
    {
        return getAttribute("HEIGHT_MM").getInt();
    }

    /**
     * Returns the lath distance needed to mount the roof tile.
     *
     * @return The lath distance needed to mount the roof tile.
     */
    default int getLathDistance()
    {
        return getAttribute("LATH_DISTANCE_MM").getInt();
    }
}
