package tvestergaard.fog.data.materials.attributes;

import java.util.Objects;

public class DefaultAttribute implements Attribute
{

    /**
     * The definition of the attribute value.
     */
    private final AttributeDefinition definition;

    /**
     * The value of the attribute.
     */
    private Object value;

    /**
     * Creates a new {@link Attribute}.
     *
     * @param definition The definition of the attribute value.
     * @param value      The attribute value.
     * @throws IncorrectDataTypeException When the provided value is not permitted by the attribute definition.
     */
    public DefaultAttribute(AttributeDefinition definition, Object value)
    {
        if (value != null && !validate(value, definition.getDataType()))
            throw new IncorrectDataTypeException();

        this.definition = definition;
        this.value = value;
    }

    /**
     * Returns the definition of the attribute value.
     *
     * @return The definition of the attribute value.
     */
    @Override public AttributeDefinition getDefinition()
    {
        return definition;
    }

    /**
     * Returns the value of the attribute.
     *
     * @return The value of the attribute.
     */
    @Override public Object getValue()
    {
        return value;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param attributeValue The new attribute value.
     * @throws IncorrectDataTypeException When the attribute does not accept a string.
     */
    @Override public void setString(String attributeValue)
    {
        if (definition.getDataType() != DataType.STRING)
            throw new IncorrectDataTypeException();

        this.value = attributeValue;
    }

    /**
     * Sets the value of the attribute to an int.
     *
     * @param attributeValue The new attribute value.
     * @throws IncorrectDataTypeException When the attribute does not accept an int.
     */
    @Override public void setInt(int attributeValue)
    {
        if (definition.getDataType() != DataType.INT)
            throw new IncorrectDataTypeException();

        this.value = attributeValue;
    }

    /**
     * Returns the value of the attribute as a string.
     *
     * @return The value of the attribute as a string.
     * @throws AttributeFormatException When the value associated with the attribute could not be converted to a the
     *                                  result format.
     */
    @Override public String getString()
    {
        if (definition.getDataType() != DataType.STRING)
            throw new AttributeFormatException();

        return (String) value;
    }

    /**
     * Returns the value of the attribute as a int.
     *
     * @return The value of the attribute as a int.
     * @throws AttributeFormatException When the value associated with the attribute could not be converted to a the
     *                                  result format.
     */
    @Override public int getInt()
    {
        if (definition.getDataType() != DataType.INT)
            throw new AttributeFormatException();

        return (int) value;
    }

    /**
     * Validates that the provided value is of the provided data type.
     *
     * @param value    The value.
     * @param dataType The data type.
     * @return {@code true} if the value is of the provided data type.
     */
    private boolean validate(Object value, DataType dataType)
    {
        if (dataType == DataType.INT)
            return value instanceof Integer;
        if (dataType == DataType.STRING)
            return value instanceof String;

        throw new IllegalStateException("Unknown data type " + dataType.name());
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;
        Attribute that = (Attribute) o;
        return Objects.equals(getDefinition(), that.getDefinition()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getDefinition(), getValue());
    }

    @Override public String toString()
    {
        return "DefaultAttribute{" +
                "definition=" + definition +
                ", value=" + value +
                '}';
    }
}
