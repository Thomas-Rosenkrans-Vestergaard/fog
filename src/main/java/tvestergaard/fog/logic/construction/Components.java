package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.components.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Components
{

    /**
     * The internal storage where the components are mapped to their identifiers.
     */
    private final Map<String, Component> components = new HashMap<>();

    /**
     * Creates a new {@link Components} from the provided list of components.
     *
     * @param components The components to add to the object.
     */
    public Components(List<Component> components)
    {
        for (Component component : components)
            this.components.put(component.getDefinition().getIdentifier(), component);
    }

    /**
     * Returns the component with the provided identifier.
     *
     * @param identifier The identifier of the component to return.
     * @return The component with the provided identifier.
     * @throws UnknownComponentException When a component with the provided identifier does not exist.
     */
    public Component from(String identifier)
    {
        Component component = components.get(identifier);
        if (component == null)
            throw new UnknownComponentException(identifier);

        return component;
    }
}
