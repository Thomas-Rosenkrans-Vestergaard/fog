package tvestergaard.fog.data;

import java.util.HashMap;

public class ComponentCollectionRecord implements ComponentCollection
{

    /**
     * The components mapped to its identifier.
     */
    private final HashMap<String, Component> components = new HashMap<>();

    /**
     * Inserts a new component into the component collection.
     *
     * @param identifier The identifier of the component to insert.
     * @param component  The component value.
     */
    public void put(String identifier, Component component)
    {
        components.put(identifier, component);
    }

    /**
     * Returns the component associated with the provided identifier.
     *
     * @param identifier The identifier of the component to return.
     * @return The component associated with the provided identifier.
     */
    @Override public Component get(String identifier)
    {
        return components.get(identifier);
    }
}
