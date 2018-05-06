package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

public interface RoofRidgeTile extends Material
{

    /**
     * Returns the length of the ridge tile.
     *
     * @return The length of the ridge tile.
     */
    default int getLength()
    {
        return getAttribute("LENGTH_MM").getInt();
    }
}
