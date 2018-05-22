package tvestergaard.fog.data.flooring;

import java.util.Objects;

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
     * Whether or not the flooring can be applied to new orders.
     */
    private boolean active;

    /**
     * Creates a new {@link MysqlFlooringDAO}.
     *
     * @param id          The unique identifier of the flooring.
     * @param name        The name of the flooring.
     * @param description The description of the flooring.
     * @param active      Whether or not the flooring can be applied to new orders.
     */
    public FlooringRecord(int id, String name, String description, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    /**
     * Returns the unique identifier of the flooring.
     *
     * @return The unique identifier of the flooring.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the flooring.
     *
     * @return The name of the flooring.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the flooring.
     *
     * @param name The updated name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the flooring.
     *
     * @return The description of the flooring.
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
     * Returns {@code true} if the flooring can currently be applied to new orders.
     *
     * @return {@link true} if the flooring can currently be applied to new orders.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active status of the flooring.
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
                active == that.isActive() &&
                Objects.equals(name, that.getName()) &&
                Objects.equals(description, that.getDescription());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }

    @Override public String toString()
    {
        return "FlooringRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
