package tvestergaard.fog.data.materials;

import java.util.Objects;

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
     * Creates a new {@link MaterialRecord}.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param price       The price of the material.
     * @param unit        The type of unit the material is in.
     */
    public MaterialRecord(int id, String number, String description, int price, int unit)
    {
        this.id = id;
        this.number = number;
        this.description = description;
        this.price = price;
        this.unit = unit;
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

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof MaterialRecord)) return false;
        MaterialRecord that = (MaterialRecord) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                getUnit() == that.getUnit() &&
                Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
