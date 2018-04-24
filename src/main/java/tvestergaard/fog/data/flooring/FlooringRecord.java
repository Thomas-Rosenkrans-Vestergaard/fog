package tvestergaard.fog.data.flooring;

import java.util.Objects;

/**
 * The default {@link Flooring} implementation.
 */
public class FlooringRecord implements Flooring
{

    /**
     * The unique identifier of the {@link Flooring}.
     */
    private final int id;

    /**
     * The name of the {@link Flooring}.
     */
    private String name;

    /**
     * The description of the {@link Flooring}.
     */
    private String description;

    /**
     * The price of the {@link Flooring} per square meter (in øre).
     */
    private int pricePerSquareMeter;

    /**
     * Whether or not the {@link Flooring} can be applied to new orders.
     */
    private boolean active;

    /**
     * Creates a new {@link MysqlFlooringDAO}.
     *
     * @param id                  The unique identifier of the {@link Flooring}.
     * @param name                The name of the {@link Flooring}.
     * @param description         The description of the {@link Flooring}.
     * @param pricePerSquareMeter The price of the {@link Flooring} per square meter (in øre).
     * @param active              Whether or not the {@link Flooring} can be applied to new orders.
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
     * Returns the unique identifier of the {@link Flooring}.
     *
     * @return The unique identifier of the {@link Flooring}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the {@link Flooring}.
     *
     * @return The name of the {@link Flooring}.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the {@link Flooring}.
     *
     * @param name The updated name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the {@link Flooring}.
     *
     * @return The description of the {@link Flooring}.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the {@lilnk Flooring}.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the price of the {@link Flooring} per square meter (in øre).
     *
     * @return The price of the {@link Flooring} per square meter (in øre).
     */
    @Override public int getPricePerSquareMeter()
    {
        return pricePerSquareMeter;
    }

    /**
     * Sets the price per square meter of {@link Flooring}.
     *
     * @param price The new price.
     */
    @Override public void setPricePerSquareMeter(int price)
    {
        this.pricePerSquareMeter = price;
    }

    /**
     * Returns {@code true} if the {@link Flooring} can currently be applied to new orders.
     *
     * @return {@link true} if the {@link Flooring} can currently be applied to new orders.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active status of the {@link Flooring}.
     *
     * @param active The new active status.
     */
    @Override public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override public boolean equals(Object o)
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

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
