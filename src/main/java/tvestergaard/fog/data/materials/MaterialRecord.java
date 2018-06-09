package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.categories.*;

import java.util.*;
import java.util.function.Function;

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

    /**
     * The id of the category the material belongs to.
     */
    private final int categoryId;

    /**
     * Whether or not the material is currently active in construction.
     */
    private final boolean active;

    /**
     * The category the material belongs to.
     */
    private final Category category;

    /**
     * The attributes assigned to the material.
     */
    private final HashMap<String, Attribute> attributes = new HashMap<>();

    /**
     * Creates a new {@link MaterialRecord}.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param price       The price of the material.
     * @param unit        The type of unit the material is in.
     * @param active      Whether or not the material is currently active in construction.
     * @param categoryId  The id of the category the material belongs to.
     * @param category    The category the material belongs to.
     * @param attributes  The attribute to add to the material.
     */
    public MaterialRecord(int id, String number, String description, int price, int unit, boolean active,
                          int categoryId, Category category, Set<Attribute> attributes)
    {
        this.id = id;
        this.number = number;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.active = active;
        this.categoryId = categoryId;
        this.category = category;
        for (Attribute attribute : attributes)
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
     * Checks if the material is currently active.
     *
     * @return {@code true} if the material is currently active. Or {@code false} or has been replaced with a newer version.
     */
    @Override public boolean isActive()
    {
        return active;
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

    private final static Map<Class<? extends Category>, Function<Material, Object>> factories = new HashMap<>();

    static {
        factories.put(Pole.class, Pole::new);
        factories.put(Board.class, Board::new);
        factories.put(RafterWood.class, RafterWood::new);
        factories.put(Reglar.class, Reglar::new);
        factories.put(RidgeLathHolder.class, RidgeLathHolder::new);
        factories.put(RoofLath.class, RoofLath::new);
        factories.put(RoofRidgeTile.class, RoofRidgeTile::new);
        factories.put(RoofTile.class, RoofTile::new);
    }

    /**
     * Returns the specific type of category the material belongs to.
     *
     * @param category The class of the category.
     * @param <T>      The type of the category.
     * @return The category instance.
     * @throws IncorrectCategoryException When the category could not be converted.
     */
    public <T extends Category> T as(Class<T> category) throws IncorrectCategoryException
    {
        Function<Material, Object> factory = factories.get(category);

        if (factory == null)
            throw new IllegalStateException("No factory for category " + category.getName());

        return category.cast(factory.apply(this));
    }

    /**
     * Returns a complete list of the attributes describing the material.
     *
     * @return The complete list.
     */
    @Override public Set<Attribute> getAttributes()
    {
        return new HashSet<>(attributes.values());
    }

    /**
     * Returns the attribute with the provided name.
     *
     * @param name The name of the attribute to return.
     * @return The attribute with the provided name. Returns null if an attribute with the provided name does not exist.
     */
    @Override public Attribute getAttribute(String name)
    {
        return attributes.get(name);
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o instanceof SimpleMaterial) {
            SimpleMaterial that = (SimpleMaterial) o;
            return getId() == that.getId() &&
                    getPrice() == that.getPrice() &&
                    getUnit() == that.getUnit() &&
                    getCategoryId() == that.getCategoryId() &&
                    isActive() == that.isActive() &&
                    Objects.equals(getNumber(), that.getNumber()) &&
                    Objects.equals(getDescription(), that.getDescription()) &&
                    Objects.equals(getCategory(), that.getCategory());
        }

        if (!(o instanceof Material)) return false;
        Material that = (Material) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                getUnit() == that.getUnit() &&
                getCategoryId() == that.getCategoryId() &&
                isActive() == that.isActive() &&
                Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCategory(), that.getCategory()) &&
                Objects.equals(getAttributes(), that.getAttributes());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getNumber(), getDescription(), getPrice(), getUnit(), getCategoryId(), isActive(), getCategory(), getAttributes());
    }

    @Override public String toString()
    {
        return "MaterialRecord{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", categoryId=" + categoryId +
                ", active=" + active +
                ", category=" + category +
                ", attributes=" + attributes +
                '}';
    }
}
