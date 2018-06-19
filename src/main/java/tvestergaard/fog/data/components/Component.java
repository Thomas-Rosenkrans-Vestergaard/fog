package tvestergaard.fog.data.components;

import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.categories.Category;
import tvestergaard.fog.data.materials.categories.IncorrectCategoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The component, with both its definition and the selected material.
 */
public interface Component extends ComponentConnection, ComponentDefinition, Material
{

    /**
     * Creates a new {@link Component} from the provided information.
     *
     * @param id         The id of the component value.
     * @param definition The definition the component value satisfies.
     * @param materials  The materials used in the component (when multiple materials are used).
     * @return The resulting {@link ComponentConnection}.
     */
    static ComponentConnection from(int id, ComponentDefinition definition, List<Material> materials)
    {
        return new ComponentRecord(id, definition.getId(), definition, materials);
    }

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
     * Whether or not the component contains multiple materials.
     *
     * @return {@code true} when the component contains multiple materials. {@code false} otherwise.
     */
    @Override default boolean isMultiple()
    {
        return getDefinition().isMultiple();
    }

    /**
     * Returns the first material assigned to the component.
     *
     * @return The first material assigned to the component.
     */
    default Material getMaterial()
    {
        return getMaterials().get(0);
    }

    /**
     * Returns the first material assigned to the component, cast to the provided category.
     *
     * @param category The class of the category.
     * @return The resulting category instance.
     * @throws IncorrectCategoryException When the category could not be converted.
     */
    default <T extends Category> T as(Class<T> category) throws IncorrectCategoryException
    {
        return getMaterial().as(category);
    }

    /**
     * Returns the materials assigned to the component.
     *
     * @return The materials assigned to the component.
     */
    List<Material> getMaterials();

    /**
     * Checks if the component contains the provided material.
     *
     * @param material The material to check for.
     * @return The result of the check.
     */
    default boolean contains(Material material)
    {
        return getMaterials().contains(material);
    }

    /**
     * Returns the materials defined in the component as the target category material type.
     *
     * @param category
     * @param <T>
     * @return Returns a list of the target material type. When the component contains only one materials, this method
     * returns {@code null}.
     * @throws IncorrectCategoryException
     */
    default <T extends Category> List<T> asMultiple(Class<T> category) throws IncorrectCategoryException
    {
        List<Material> before = getMaterials();
        if (before == null)
            return null;

        List<T> after = new ArrayList<>();
        for (Material material : before)
            after.add(material.as(category));

        return after;
    }

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
