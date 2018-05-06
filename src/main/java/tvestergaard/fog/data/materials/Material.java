package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.materials.categories.Category;

public interface Material extends MaterialUpdater
{

    /**
     * Returns the category the material belongs to.
     *
     * @return The category the material belongs to.
     */
    Category getCategory();
}
