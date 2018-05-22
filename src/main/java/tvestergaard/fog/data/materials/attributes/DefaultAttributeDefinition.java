package tvestergaard.fog.data.materials.attributes;

public class DefaultAttributeDefinition implements AttributeDefinition
{

    /**
     * Returns the id of the attribute definition.
     */
    private final int id;

    /**
     * The name of the attribute.
     */
    private final String name;

    /**
     * The data type of the attribute.
     */
    private final DataType dataType;

    /**
     * Creates a new {@link DefaultAttributeDefinition}.
     *
     * @param id       The id of the attribute.
     * @param name     The name of the attribute.
     * @param dataType The data type of the attribute.
     */
    public DefaultAttributeDefinition(int id, String name, DataType dataType)
    {
        this.id = id;
        this.name = name;
        this.dataType = dataType;
    }

    /**
     * Returns the id of the attribute definition.
     *
     * @return The id of the attribute definition.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the attribute.
     *
     * @return The name of the attribute.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Returns the data type of the attribute.
     *
     * @return The data type of the attribute.
     */
    @Override public DataType getDataType()
    {
        return dataType;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultAttributeDefinition that = (DefaultAttributeDefinition) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        return dataType == that.dataType;
    }

    @Override public int hashCode()
    {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + dataType.hashCode();
        return result;
    }

    @Override public String toString()
    {
        return "DefaultAttributeDefinition{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", dataType=" + dataType +
               '}';
    }
}
