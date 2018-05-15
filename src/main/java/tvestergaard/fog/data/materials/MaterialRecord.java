package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.materials.attributes.AttributeValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MaterialRecord implements Material
{

    /**
     * The unique identifier of the material.
     */
    private final int id;

    /**
     * The material number.
     */
    private final String number;

    /**
     * The description of the material.
     */
    private String description;

    /**
     * The price of the material.
     */
    private int price;

    /**
     * The unit of the materials.
     */
    private int unit;

    private final int categoryId;

    /**
     * The category the material belongs to.
     */
    private final Category category;

    private final HashMap<String, AttributeValue> attributes = new HashMap<>();

    /**
     * Creates a new {@link MaterialRecord}.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param price       The price of the material.
     * @param unit        The type of unit the material is in.
     * @param category    The category the material belongs to.
     * @param attributes  The attribute to add to the material.
     */
    public MaterialRecord(int id, String number, String description, int price, int unit, int categoryId, Category category, Set<AttributeValue> attributes)
    {
        this.id = id;
        this.number = number;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.categoryId = categoryId;
        this.category = category;
        for (AttributeValue attribute : attributes)
            this.attributes.put(attribute.getDefinition().getName(), attribute);
    }

    /**
     * Returns the unique identifier of the material.
     *
     * @return The unique identifier of the material.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the material number.
     *
     * @return The material number.
     */
    @Override public String getNumber()
    {
        return number;
    }

    /**
     * Returns the description of the material.
     *
     * @return The description of the material.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the material.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the price of the material.
     *
     * @return The price of the material.
     */
    @Override public int getPrice()
    {
        return price;
    }

    /**
     * Sets the price of the material.
     *
     * @param price The new price.
     */
    @Override public void setPrice(int price)
    {
        this.price = price;
    }

    /**
     * Returns the unit size of the material.
     *
     * @return the unit size of the material.
     */
    @Override public int getUnit()
    {
        return unit;
    }

    /**
     * Sets the unit size of the material.
     *
     * @param unit The new unit size of the material.
     */
    @Override public void setUnit(int unit)
    {
        this.unit = unit;
    }

    /**
     * Returns the if of the category the material belongs to.
     *
     * @return The if of the category the material belongs to.
     */
    @Override public int getCategoryId()
    {
        return categoryId;
    }

    /**
     * Returns the category the material belongs to.
     *
     * @return The category the material belongs to.
     */
    @Override public Category getCategory()
    {
        return category;
    }

    /**
     * Returns a complete list of the attributes describing the material.
     *
     * @return The complete list.
     */
    @Override public Set<AttributeValue> getAttributes()
    {
        return new HashSet<>(attributes.values());
    }

    /**
     * Returns the attribute with the provided name.
     *
     * @param name The name of the attribute to return.
     * @return The attribute with the provided name. Returns null if an attribute with the provided name does not exist.
     */
    @Override public AttributeValue getAttribute(String name)
    {
        return attributes.get(name);
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        Material that = (Material) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                getUnit() == that.getUnit() &&
                Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getCategory() == that.getCategory();
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getNumber(), getDescription(), getPrice(), getUnit(), getCategory());
    }

    @Override public String toString()
    {
        return "MaterialRecord{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", category=" + category +
                '}';
    }
}
