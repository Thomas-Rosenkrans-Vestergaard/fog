package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RoofTile extends AbstractCategoryMaterial
{

    /**
     * Creates a new {@link RoofTile} category.
     *
     * @param material The material to extract attributes from.
     * @throws IncorrectCategoryException When the provided material is not of the correct category.
     */
    public RoofTile(Material material)
    {
        super(material);

        if (material.getCategory().getId() != 5)
            throw new IncorrectCategoryException();
    }

    /**
     * Returns the width of the roof tile in millimeters.
     *
     * @return The width of the roof tile in millimeters.
     */
    public int getWidth()
    {
        Attribute attribute = material.getAttribute("WIDTH_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }

    /**
     * Returns the height of the roof tile in millimeters.
     *
     * @return The height of the roof tile in millimeters.
     */
    public int getHeight()
    {
        Attribute attribute = material.getAttribute("HEIGHT_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }

    /**
     * Returns the distance the laths must be placed at. The distance is returned in millimeters.
     *
     * @return The distance the laths must be placed at.
     */
    public int getLathDistance()
    {
        Attribute attribute = material.getAttribute("LATH_DISTANCE_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }
}
