package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class Board extends AbstractWoodCategory
{

    /**
     * Creates a new {@link Board} category.
     *
     * @param material The material to extract attributes from.
     */
    public Board(Material material)
    {
        super(material);
    }
}
