package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RoofLath extends AbstractWoodCategory
{

    /**
     * Creates a new {@link RoofLath} category.
     *
     * @param material The material to extract attributes from.
     */
    public RoofLath(Material material)
    {
        super(material);
    }
}
