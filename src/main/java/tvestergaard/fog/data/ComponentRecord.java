package tvestergaard.fog.data;

import tvestergaard.fog.data.materials.Material;

import java.util.HashMap;

public class ComponentRecord implements Component
{

    /**
     * The material used in the component.
     */
    private final Material material;

    /**
     * The attributes assigned to the component.
     */
    private HashMap<String, String> attributes = new HashMap<>();

    /**
     * Creates a new {@link ComponentRecord}.
     *
     * @param material The material used in the component.
     */
    public ComponentRecord(Material material)
    {
        this.material = material;
    }

    /**
     * Returns the material used for the component.
     *
     * @return The material used for the component.
     */
    @Override public Material getMaterial()
    {
        return material;
    }

    /**
     * Inserts a new attribute pair into the component.
     *
     * @param attributeName  The name of the attribute.
     * @param attributeValue The value of the attribute.
     */
    public void put(String attributeName, String attributeValue)
    {
        attributes.put(attributeName, attributeValue);
    }

    /**
     * Returns the string value of the provided attribute name.
     *
     * @param attribute The name of the attribute to return the value of.
     * @return The value associated with the provided attribute name.
     */
    @Override public String getString(String attribute)
    {
        return attributes.get(attribute);
    }
}
