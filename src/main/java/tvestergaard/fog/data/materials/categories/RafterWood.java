package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RafterWood extends AbstractWoodCategory
{

    /**
     * Creates a new {@link RafterWood} category.
     *
     * @param material The material to extract attributes from.
     */
    public RafterWood(Material material)
    {
        super(material);
    }
}
