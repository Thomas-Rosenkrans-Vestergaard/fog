package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Category;
import tvestergaard.fog.data.materials.Material;

public abstract class AbstractCategory implements Category
{

    /**
     * The inner category that this abstract class wraps.
     */
    protected final Category category;

    /**
     * The material from which to extract attribute values.
     */
    protected final Material material;

    /**
     * Creates a new {@link AbstractCategory}.
     *
     * @param material The material from which to extract attribute values.
     */
    public AbstractCategory(Material material)
    {
        this.category = material.getCategory();
        this.material = material;
    }

    /**
     * Returns the id of the category.
     *
     * @return The id of the category.
     */
    @Override public int getId()
    {
        return category.getId();
    }

    /**
     * Returns the name of the category.
     *
     * @return The name of the category.
     */
    @Override public String getName()
    {
        return category.getName();
    }
}
