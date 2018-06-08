package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

public interface Category
{

    /**
     * Returns the id of the category.
     *
     * @return The id of the category.
     */
    int getId();

    /**
     * Returns the name of the category.
     *
     * @return The name of the category.
     */
    String getName();
}
