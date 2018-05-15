package tvestergaard.fog.data.materials.attributes;

import java.util.Objects;

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
        if (!(o instanceof DefaultAttributeDefinition)) return false;
        DefaultAttributeDefinition that = (DefaultAttributeDefinition) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName()) &&
                getDataType() == that.getDataType();
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getName(), getDataType());
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
