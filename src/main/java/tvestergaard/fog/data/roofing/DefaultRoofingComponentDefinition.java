package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.materials.Category;

public class DefaultRoofingComponentDefinition implements RoofingComponentDefinition
{

    private final int         id;
    private final RoofingType roofingType;
    private final String      identifier;
    private final Category    category;

    public DefaultRoofingComponentDefinition(int id, RoofingType roofingType, String identifier, Category category)
    {
        this.id = id;
        this.roofingType = roofingType;
        this.identifier = identifier;
        this.category = category;
    }

    /**
     * Returns the id of the roofing component definition.
     *
     * @return The id of the roofing component definition.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the roofing type the component is defined for.
     *
     * @return The roofing type the component is defined for.
     */
    @Override public RoofingType getRoofingType()
    {
        return roofingType;
    }

    /**
     * Returns the identifier of the roofing component.
     *
     * @return The identifier of the roofing component.
     */
    @Override public String getIdentifier()
    {
        return identifier;
    }

    /**
     * Returns the material category that the roofing component must be of.
     *
     * @return The material category that the roofing component must be of.
     */
    @Override public Category getCategory()
    {
        return category;
    }
}
