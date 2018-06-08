package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;

public class AbstractWoodCategory extends AbstractCategory
{

    /**
     * Creates a new {@link AbstractCategory}.
     *
     * @param material The material from which to extract attribute values.
     */
    public AbstractWoodCategory(Material material)
    {
        super(material);
    }

    /**
     * Returns the length of the wood in millimeters.
     *
     * @return The length of the wood in millimeters.
     */
    public int getLength()
    {
        Attribute attribute = material.getAttribute("LENGTH_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }

    /**
     * Returns the width of the wood in millimeters. The width is the wider of the two edges.
     *
     * @return The width of the wood in millimeters.
     */
    public int getWidth()
    {
        Attribute attribute = material.getAttribute("WIDTH_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }

    /**
     * Returns the thickness of the wood in millimeters. The thickness is the narrower of the two edges.
     *
     * @return The thickness of the wood in millimeters.
     */
    public int getThickness()
    {
        Attribute attribute = material.getAttribute("THICKNESS_MM");

        if (attribute == null)
            throw new IllegalStateException();

        return attribute.getInt();
    }
}
