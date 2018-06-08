package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;

/**
 * Provides a type safe method for extracting attributes from some material.
 */
public class Pole extends AbstractCategory
{

    /**
     * Creates a new {@link Pole} category.
     *
     * @param material The material to extract attributes from.
     * @throws IncorrectCategoryException When the provided material is not of the correct category.
     */
    public Pole(Material material)
    {
        super(material);

        if (material.getCategory().getId() != 4)
            throw new IncorrectCategoryException();
    }

    /**
     * Returns the length of the pole in millimeters.
     *
     * @return The length of the pole in millimeters.
     */
    public int getLength()
    {
        Attribute attribute = material.getAttribute("LENGTH_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }

    /**
     * Returns the thickness of the pole in millimeters.
     *
     * @return The thickness of the pole in millimeters.
     */
    public int getThickness()
    {
        Attribute attribute = material.getAttribute("THICKNESS_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }
}
