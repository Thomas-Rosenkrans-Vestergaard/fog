package tvestergaard.fog.data.flooring;

import java.util.Objects;

/**
 * The default {@link Flooring} implementation.
 */
public class FlooringRecord implements Flooring
{

    /**
     * The unique identifier of the flooring.
     */
    private final int id;

    /**
     * The name of the flooring.
     */
    private String name;

    /**
     * The description of the flooring.
     */
    private String description;

    /**
     * The price of the flooring per square meter (in øre).
     */
    private int pricePerSquareMeter;

    /**
     * Whether or not the flooring can be applied to new orders.
     */
    private boolean active;

    /**
     * Creates a new {@link MysqlFlooringDAO}.
     *
     * @param id                  The unique identifier of the flooring.
     * @param name                The name of the flooring.
     * @param description         The description of the flooring.
     * @param pricePerSquareMeter The price of the flooring per square meter (in øre).
     * @param active              Whether or not the flooring can be applied to new orders.
     */
    public FlooringRecord(int id, String name, String description, int pricePerSquareMeter, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerSquareMeter = pricePerSquareMeter;
        this.active = active;
    }

    /**
     * Returns the unique identifier of the flooring.
     *
     * @return The unique identifier of the flooring.
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the flooring.
     *
     * @return The name of the flooring.
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the flooring.
     *
     * @param name The updated name.
     */
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the flooring.
     *
     * @return The description of the flooring.
     */
    @Override
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the {@lilnk Flooring}.
     *
     * @param description The new description.
     */
    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the price of the flooring per square meter (in øre).
     *
     * @return The price of the flooring per square meter (in øre).
     */
    @Override
    public int getPricePerSquareMeter()
    {
        return pricePerSquareMeter;
    }

    /**
     * Sets the price per square meter of flooring.
     *
     * @param price The new price.
     */
    @Override
    public void setPricePerSquareMeter(int price)
    {
        this.pricePerSquareMeter = price;
    }

    /**
     * Returns {@code true} if the flooring can currently be applied to new orders.
     *
     * @return {@link true} if the flooring can currently be applied to new orders.
     */
    @Override
    public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active status of the flooring.
     *
     * @param active The new active status.
     */
    @Override
    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Flooring)) return false;
        Flooring that = (Flooring) o;
        return id == that.getId() &&
                pricePerSquareMeter == that.getPricePerSquareMeter() &&
                active == that.isActive() &&
                Objects.equals(name, that.getName()) &&
                Objects.equals(description, that.getDescription());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
