package tvestergaard.fog.data.materials.attributes;

public interface Attribute
{

    /**
     * Returns the definition of the attribute value.
     *
     * @return The definition of the attribute value.
     */
    AttributeDefinition getDefinition();

    /**
     * Returns the value of the attribute.
     *
     * @return The value of the attribute.
     */
    Object getValue();

    /**
     * Returns {@code true} if the value in the attribute is {@code null}.
     *
     * @return {@code true} if the value in the attribute is {@code null}.
     */
    default boolean isNull()
    {
        return getValue() == null;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param attributeValue The new attribute value.
     * @throws IncorrectDataTypeException When the attribute does not accept a string.
     */
    void setString(String attributeValue);

    /**
     * Sets the value of the attribute to an int.
     *
     * @param attributeValue The new attribute value.
     * @throws IncorrectDataTypeException When the attribute does not accept an int.
     */
    void setInt(Integer attributeValue);

    /**
     * Returns the value of the attribute as a string.
     *
     * @return The value of the attribute as a string.
     * @throws IncorrectDataTypeException  When the value attribute value could not be converted to a string.
     * @throws AttributeValueNullException When the value attribute is {@code null}.
     */
    String getString();

    /**
     * Returns the value of the attribute as an integer.
     *
     * @return The value of the attribute as a int.
     * @throws IncorrectDataTypeException  When the value attribute value could not be converted to an integer.
     * @throws AttributeValueNullException When the value attribute is {@code null}.
     */
    Integer getInt();
}
