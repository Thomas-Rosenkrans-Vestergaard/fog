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
     * The minimum slope the roofing must have.
     */
    private int minimumSlope;

    /**
     * The maximum slope the roofing must have.
     */
    private int maximumSlope;

    /**
     * Whether or not the roofing can currently be applied to new orders.
     */
    private boolean active;

    /**
     * Creates a new {@link RoofingRecord}.
     *
     * @param id           The unique identifier of the roofing.
     * @param name         The name of the roofing.
     * @param description  The description of the roofing.
     * @param minimumSlope The minimum slope the roofing must have.
     * @param maximumSlope The maximum slope the roofing must have.
     * @param active       Whether or not the roofing can currently be applied to new orders.
     */
    public RoofingRecord(int id, String name, String description, int minimumSlope, int maximumSlope, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minimumSlope = minimumSlope;
        this.maximumSlope = maximumSlope;
        this.active = active;
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
    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the minimum slope the roofing must have.
     *
     * @return The minimum slope the roofing must have. Returns an integer between 0 and 90 (exclusive).
     */
    @Override public int getMinimumSlope()
    {
        return minimumSlope;
    }

    /**
     * Sets the minimum slope the roofing must have.
     *
     * @param minimumSlope The new minimum slope. Range between 0 and 90 (exclusive).
     */
    @Override public void setMinimumSlope(int minimumSlope)
    {
        this.minimumSlope = minimumSlope;
    }

    /**
     * Returns the maximum slope the roofing must have.
     *
     * @return The maximum slope the roofing must have. Returns an integer between 0 and 90 (exclusive).
     */
    @Override public int getMaximumSlope()
    {
        return maximumSlope;
    }

    /**
     * Sets the maximum slope the roofing can have.
     *
     * @param maximumSlope The new maximum slope. Range between 0 and 90 (exclusive).
     */
    @Override public void setMaximumSlope(int maximumSlope)
    {
        this.maximumSlope = maximumSlope;
    }

    /**
     * Returns {@code true} if the roofing can currently be applied to new orders.
     *
     * @return {@link true} if the roofing can currently be applied to new orders.
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
    @Override
    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Roofing)) return false;
        Roofing that = (Roofing) o;
        return id == that.getId() &&
                minimumSlope == that.getMinimumSlope() &&
                maximumSlope == that.getMaximumSlope() &&
                active == that.isActive() &&
                Objects.equals(name, that.getName()) &&
                Objects.equals(description, that.getDescription());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
