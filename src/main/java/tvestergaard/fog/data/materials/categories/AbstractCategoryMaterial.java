package tvestergaard.fog.data.materials.categories;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.MaterialBlueprint;
import tvestergaard.fog.data.materials.MaterialUpdater;
import tvestergaard.fog.data.materials.attributes.Attribute;

import java.util.Set;

public class AbstractCategoryMaterial implements CategoryMaterial, Material
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
     * Creates a new {@link AbstractCategoryMaterial}.
     *
     * @param material The material from which to extract attribute values.
     */
    public AbstractCategoryMaterial(Material material)
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
     * Creates a new material blueprint using the provided information.
     *
     * @param number      The material number to specify in the blueprint.
     * @param description The material description to specify in the blueprint.
     * @param price       The price of the material.
     * @param unit        The unit size of the material.
     * @param category    The category the material belongs to.
     * @param attributes  The attributes of the material specified in the blueprint.
     * @return The resulting blueprint.
     */
    public static MaterialBlueprint from(String number, String description, int price, int unit, int category, Set<Attribute> attributes)
    {
        return MaterialBlueprint.from(number, description, price, unit, category, attributes);
    }

    /**
     * Returns the material number.
     *
     * @return The material number.
     */
    @Override public String getNumber()
    {
        return material.getNumber();
    }

    /**
     * Returns the description of the material.
     *
     * @return The description of the material.
     */
    @Override public String getDescription()
    {
        return material.getDescription();
    }

    /**
     * Sets the description of the material.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        material.setDescription(description);
    }

    /**
     * Returns the price of the material.
     *
     * @return The price of the material.
     */
    @Override public int getPrice()
    {
        return material.getPrice();
    }

    /**
     * Sets the price of the material.
     *
     * @param price The new price.
     */
    @Override public void setPrice(int price)
    {
        material.setPrice(price);
    }

    /**
     * Returns the unit size of the material.
     *
     * @return the unit size of the material.
     */
    @Override public int getUnit()
    {
        return material.getUnit();
    }

    /**
     * Sets the unit size of the material.
     *
     * @param unit The new unit size of the material.
     */
    @Override public void setUnit(int unit)
    {
        material.setUnit(unit);
    }

    /**
     * Returns the if of the category the material belongs to.
     *
     * @return The if of the category the material belongs to.
     */
    @Override public int getCategoryId()
    {
        return material.getCategoryId();
    }

    /**
     * Returns a complete set of the attributes describing the material.
     *
     * @return The complete set.
     */
    @Override public Set<Attribute> getAttributes()
    {
        return material.getAttributes();
    }

    /**
     * Returns the attribute with the provided name.
     *
     * @param name The name of the attribute to return.
     * @return The attribute with the provided name. Returns null if an attribute with the provided name does not exist.
     */
    @Override public Attribute getAttribute(String name)
    {
        return material.getAttribute(name);
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

    /**
     * Checks if the material is currently active.
     *
     * @return {@code true} if the material is currently active. Or {@code false} or has been replaced with a newer version.
     */
    @Override public boolean isActive()
    {
        return material.isActive();
    }

    /**
     * Returns the category the material belongs to.
     *
     * @return The category the material belongs to.
     */
    @Override public Category getCategory()
    {
        return material.getCategory();
    }

    /**
     * Returns the specific type of category the material belongs to.
     *
     * @param category The class of the category.
     * @return The category instance.
     * @throws IncorrectCategoryException When the category could not be converted.
     */
    @Override public <T extends Category> T as(Class<T> category) throws IncorrectCategoryException
    {
        return material.as(category);
    }

    /**
     * Creates a new material updater using the provided information.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param price       The price of the material.
     * @param unit        The type of unit the material is in.
     * @param category    The category the material belongs to.
     * @param attributes  The attributes to add to the material.
     * @return The resulting updater.
     */
    public static MaterialUpdater from(int id, String number, String description, int price, int unit, int category, Set<Attribute> attributes)
    {
        return MaterialUpdater.from(id, number, description, price, unit, category, attributes);
    }
}
