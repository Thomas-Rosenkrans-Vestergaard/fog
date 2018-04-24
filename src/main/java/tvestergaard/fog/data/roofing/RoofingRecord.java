package tvestergaard.fog.data.roofing;

/**
 * The default implementation of {@link Roofing}.
 */
public class RoofingRecord implements Roofing
{

    /**
     * The unique identifier of the {@link Roofing}.
     */
    private final int id;

    /**
     * The name of the {@link Roofing}.
     */
    private String name;

    /**
     * The description of the {@link Roofing}.
     */
    private String description;

    /**
     * The minimum slope the {@link Roofing} must have.
     */
    private int minimumSlope;

    /**
     * The maximum slope the {@link Roofing} must have.
     */
    private int maximumSlope;

    /**
     * The price of the {@link Roofing} per square meter (in øre).
     */
    private int pricePerSquareMeter;

    /**
     * Creates a new {@link RoofingRecord}.
     *
     * @param id                  The unique identifier of the {@link Roofing}.
     * @param name                The name of the {@link Roofing}.
     * @param description         The description of the {@link Roofing}.
     * @param minimumSlope        The minimum slope the {@link Roofing} must have.
     * @param maximumSlope        The maximum slope the {@link Roofing} must have.
     * @param pricePerSquareMeter The price of the {@link Roofing} per square meter (in øre).
     */
    public RoofingRecord(int id, String name, String description, int minimumSlope, int maximumSlope, int pricePerSquareMeter)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minimumSlope = minimumSlope;
        this.maximumSlope = maximumSlope;
        this.pricePerSquareMeter = pricePerSquareMeter;
    }

    /**
     * Returns the unique identifier of the {@link Roofing}.
     *
     * @return The unique identifier of the {@link Roofing}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the {@link Roofing}.
     *
     * @return The name of the {@link Roofing}.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the {@link Roofing}.
     *
     * @param name The new name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the {@link Roofing}.
     *
     * @return The description of the {@link Roofing}.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the {@link Roofing}.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the minimum slope the {@link Roofing} must have.
     *
     * @return The minimum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    @Override public int getMinimumSlope()
    {
        return minimumSlope;
    }

    /**
     * Sets the minimum slope the {@link Roofing} must have.
     *
     * @param minimumSlope The new minimum slope. Range between 0 and 90 (exclusive).
     */
    @Override public void setMinimumSlope(int minimumSlope)
    {
        this.minimumSlope = minimumSlope;
    }

    /**
     * Returns the maximum slope the {@link Roofing} must have.
     *
     * @return The maximum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    @Override public int getMaximumSlope()
    {
        return maximumSlope;
    }

    /**
     * Sets the maximum slope the {@link Roofing} can have.
     *
     * @param maximumSlope The new maximum slope. Range between 0 and 90 (exclusive).
     */
    @Override public void setMaximumSlope(int maximumSlope)
    {
        this.maximumSlope = maximumSlope;
    }

    /**
     * Returns the price of the {@link Roofing} per square meter (in øre).
     *
     * @return The price of the {@link Roofing} per square meter (in øre).
     */
    @Override public int getPricePerSquareMeter()
    {
        return pricePerSquareMeter;
    }

    /**
     * Sets the price of the {@link Roofing} per square meter.
     *
     * @param price The new price (in øre).
     */
    @Override public void setPricePerSquareMeter(int price)
    {
        this.pricePerSquareMeter = price;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoofingRecord that = (RoofingRecord) o;

        return id == that.id;
    }

    @Override public int hashCode()
    {
        return id;
    }
}
