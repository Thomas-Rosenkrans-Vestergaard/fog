package tvestergaard.fog.data;

import tvestergaard.fog.data.materials.Material;

public interface Component
{

    /**
     * Returns the material used for the component.
     *
     * @return The material used for the component.
     */
    Material getMaterial();

    /**
     * Returns the string value of the provided attribute name.
     *
     * @param attribute The name of the attribute to return the value of.
     * @return The value associated with the provided attribute name.
     */
    String getString(String attribute);

    /**
     * Returns the value of the provided attribute name parsed as an int.
     *
     * @param attribute The name of the attribute to return the value of.
     * @return The value of the provided attribute name parsed as an int.
     * @throws AttributeConversionException When the attribute value cannot be converted to an int.
     */
    default int getInt(String attribute) throws AttributeConversionException
    {
        try {
            return Integer.parseInt(getString(attribute));
        } catch (Exception e) {
            throw new AttributeConversionException();
        }
    }

    /**
     * Returns the value of the provided attribute name parsed as an float.
     *
     * @param attribute The name of the attribute to return the value of.
     * @return The value of the provided attribute name parsed as an float.
     * @throws AttributeConversionException When the attribute value cannot be converted to an float.
     */
    default float getFloat(String attribute) throws AttributeConversionException
    {
        try {
            return Float.parseFloat(getString(attribute));
        } catch (Exception e) {
            throw new AttributeConversionException();
        }
    }
}
