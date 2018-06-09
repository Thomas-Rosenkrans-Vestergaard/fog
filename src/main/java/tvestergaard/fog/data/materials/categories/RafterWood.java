package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RafterWood extends AbstractWoodCategoryMaterial
{

    /**
     * Creates a new {@link RafterWood} category.
     *
     * @param material The material to extract attributes from.
     * @throws IncorrectCategoryException When the provided material is not of the correct category.
     */
    public RafterWood(Material material)
    {
        super(material);

        if (material.getCategory().getId() != 2)
            throw new IncorrectCategoryException();
    }
}
