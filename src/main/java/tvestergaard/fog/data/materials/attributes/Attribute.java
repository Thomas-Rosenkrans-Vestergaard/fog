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
    void setInt(int attributeValue);

    /**
     * Returns the value of the attribute as a string.
     *
     * @return The value of the attribute as a string.
     * @throws AttributeFormatException When the value associated with the attribute could not be converted to a the
     *                                  result format.
     */
    String getString();

    /**
     * Returns the value of the attribute as a int.
     *
     * @return The value of the attribute as a int.
     * @throws AttributeFormatException When the value associated with the attribute could not be converted to a the
     *                                  result format.
     */
    int getInt();
}
