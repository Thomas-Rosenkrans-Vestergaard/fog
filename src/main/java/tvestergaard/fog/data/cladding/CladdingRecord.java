package tvestergaard.fog.data.cladding;

/**
 * The default {@link Cladding} implementation.
 */
public class CladdingRecord implements Cladding
{

    /**
     * The unique identifier of the {@link Cladding}.
     */
    private final int id;

    /**
     * The name of the {@link Cladding}.
     */
    private String name;

    /**
     * The description of the {@link Cladding}.
     */
    private String description;

    /**
     * The price of the {@link Cladding} per square meter (in øre).
     */
    private int pricePerSquareMeter;

    /**
     * Creates a new {@link MysqlCladdingDAO}.
     *
     * @param id                  The unique identifier of the {@link Cladding}.
     * @param name                The name of the {@link Cladding}.
     * @param description         The description of the {@link Cladding}.
     * @param pricePerSquareMeter The price of the {@link Cladding} per square meter (in øre).
     */
    public CladdingRecord(int id, String name, String description, int pricePerSquareMeter)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerSquareMeter = pricePerSquareMeter;
    }

    /**
     * Returns the unique identifier of the {@link Cladding}.
     *
     * @return The unique identifier of the {@link Cladding}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the {@link Cladding}.
     *
     * @return The name of the {@link Cladding}.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the {@link Cladding}.
     *
     * @param name The updated name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the {@link Cladding}.
     *
     * @return The description of the {@link Cladding}.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the {@lilnk Cladding}.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the price of the {@link Cladding} per square meter (in øre).
     *
     * @return The price of the {@link Cladding} per square meter (in øre).
     */
    @Override public int getPricePerSquareMeter()
    {
        return pricePerSquareMeter;
    }

    /**
     * Sets the price per square meter of {@link Cladding}.
     *
     * @param price The new price.
     */
    @Override public void setPricePerSquareMeter(int price)
    {
        this.pricePerSquareMeter = price;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CladdingRecord that = (CladdingRecord) o;

        return id == that.id;
    }

    @Override public int hashCode()
    {
        return id;
    }
}
