package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.categories.Category;
import tvestergaard.fog.data.materials.categories.IncorrectCategoryException;

import java.util.Set;

public interface Component extends ComponentConnection, ComponentDefinition, Material
{

    /**
     * Returns the component definition the value is assigned to.
     *
     * @return The component definition the value is assigned to.
     */
    ComponentDefinition getDefinition();

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    default int getId()
    {
        return getDefinition().getId();
    }

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    default String getIdentifier()
    {
        return getDefinition().getIdentifier();
    }

    /**
     * Returns the notes of the roofing component.
     *
     * @return The notes of the roofing component.
     */
    default String getNotes()
    {
        return getDefinition().getNotes();
    }

    /**
     * Returns id of the material category that the component must be of.
     *
     * @return The id of the material category that the component must be of.
     */
    @Override default int getCategoryId()
    {
        return getDefinition().getCategoryId();
    }

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    default Category getCategory()
    {
        return getDefinition().getCategory();
    }

    /**
     * Returns the specific type of category the material belongs to.
     *
     * @param category The class of the category.
     * @return The category instance.
     * @throws IncorrectCategoryException When the category could not be converted.
     */
    default <T extends Category> T getCategory(Class<T> category) throws IncorrectCategoryException
    {
        return getMaterial().getCategory(category);
    }

    /**
     * Returns the material assigned to the component definition.
     *
     * @return The material assigned to the component definition.
     */
    Material getMaterial();

    /**
     * Checks if the material is currently active.
     *
     * @return {@code true} if the material is currently active. Or {@code false} or has been replaced with a newer version.
     */
    @Override default boolean isActive()
    {
        return getMaterial().isActive();
    }

    /**
     * Returns the material number.
     *
     * @return The material number.
     */
    @Override default String getNumber()
    {
        return getMaterial().getNumber();
    }

    /**
     * Returns the description of the material.
     *
     * @return The description of the material.
     */
    @Override default String getDescription()
    {
        return getMaterial().getDescription();
    }

    /**
     * Sets the description of the material.
     *
     * @param description The new description.
     */
    @Override default void setDescription(String description)
    {
        getMaterial().setDescription(description);
    }

    /**
     * Returns the price of the material.
     *
     * @return The price of the material.
     */
    @Override default int getPrice()
    {
        return getMaterial().getPrice();
    }

    /**
     * Sets the price of the material.
     *
     * @param price The new price.
     */
    @Override default void setPrice(int price)
    {
        getMaterial().setPrice(price);
    }

    /**
     * Returns the unit size of the material.
     *
     * @return the unit size of the material.
     */
    @Override default int getUnit()
    {
        return getMaterial().getUnit();
    }

    /**
     * Sets the unit size of the material.
     *
     * @param unit The new unit size of the material.
     */
    @Override default void setUnit(int unit)
    {
        getMaterial().setUnit(unit);
    }

    /**
     * Returns a complete set of the attributes describing the material.
     *
     * @return The complete set.
     */
    @Override default Set<Attribute> getAttributes()
    {
        return getMaterial().getAttributes();
    }

    /**
     * Returns the attribute with the provided name.
     *
     * @param name The name of the attribute to return.
     * @return The attribute with the provided name. Returns null if an attribute with the provided name does not exist.
     */
    @Override default Attribute getAttribute(String name)
    {
        return getMaterial().getAttribute(name);
    }
}
