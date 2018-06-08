package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class Reglar extends AbstractWoodCategory
{

    /**
     * Creates a new {@link Reglar} category.
     *
     * @param material The material to extract attributes from.
     */
    public Reglar(Material material)
    {
        super(material);
    }
}
