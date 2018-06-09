package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RidgeLathHolder extends AbstractCategoryMaterial
{

    /**
     * Creates a new {@link RidgeLathHolder} category.
     *
     * @param material The material to extract attributes from.
     * @throws IncorrectCategoryException When the provided material is not of the correct category.
     */
    public RidgeLathHolder(Material material)
    {
        super(material);

        if (material.getCategory().getId() != 7)
            throw new IncorrectCategoryException();
    }

    /**
     * Returns the interval the holders should be placed at in millimeters.
     *
     * @return The interval the holders should be placed at in millimeters.
     */
    public int getUseDistance()
    {
        Attribute attribute = material.getAttribute("USE_DISTANCE_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }
}
