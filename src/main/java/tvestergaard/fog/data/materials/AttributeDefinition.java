package tvestergaard.fog.data.materials;

public interface AttributeDefinition
{

    /**
     * Returns the id of the attribute definition.
     *
     * @return The id of the attribute definition.
     */
    int getId();

    /**
     * Returns the name of the attribute.
     *
     * @return The name of the attribute.
     */
    String getName();

    /**
     * Returns the data type of the attribute.
     *
     * @return The data type of the attribute.
     */
    DataType getDataType();
}
