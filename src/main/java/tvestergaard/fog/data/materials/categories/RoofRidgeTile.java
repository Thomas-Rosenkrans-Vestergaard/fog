package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class RoofRidgeTile extends AbstractCategory
{

    /**
     * Creates a new {@link RoofRidgeTile} category.
     *
     * @param material The material to extract attributes from.
     */
    public RoofRidgeTile(Material material)
    {
        super(material);
    }

    /**
     * Returns the length of the roof tile in millimeters.
     *
     * @return The length of the roof tile in millimeters.
     */
    public int getLength()
    {
        Attribute attribute = material.getAttribute("LENGTH_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }
}
