package tvestergaard.fog.data.roofing;

import java.util.Objects;

public class RoofingRecord implements Roofing
{

    /**
     * The unique identifier of the roofing.
     */
    private final int id;

    /**
     * The name of the roofing.
     */
    private String name;

    /**
     * The description of the roofing.
     */
    private String description;

    /**
     * Whether or not the roofing can currently be applied to new orders.
     */
    private boolean active;

    /**
     * The type of roofing.
     */
    private RoofingType type;

    /**
     * Creates a new {@link RoofingRecord}.
     *
     * @param id          The unique identifier of the roofing.
     * @param name        The name of the roofing.
     * @param description The description of the roofing.
     * @param active      Whether or not the roofing can currently be applied to new orders.
     * @param type        The type of roofing.
     */
    public RoofingRecord(int id, String name, String description, boolean active, RoofingType type)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.type = type;
    }

    /**
     * Returns the unique identifier of the roofing.
     *
     * @return The unique identifier of the roofing.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the roofing.
     *
     * @return The name of the roofing.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the roofing.
     *
     * @param name The new name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the roofing.
     *
     * @return The description of the roofing.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the roofing.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the type of the roofing.
     *
     * @return The type of the roofing.
     */
    @Override public RoofingType getType()
    {
        return type;
    }

    /**
     * Returns {@code true} if the roofing can currently be applied to new orders.
     *
     * @return {@code true} if the roofing can currently be applied to new orders.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active status of the roofing.
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
        if (!(o instanceof Roofing)) return false;
        Roofing that = (Roofing) o;
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
        return "RoofingRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", type=" + type +
                '}';
    }
}
