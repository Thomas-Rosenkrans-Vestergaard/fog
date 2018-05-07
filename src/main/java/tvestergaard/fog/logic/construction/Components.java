package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.roofing.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Components
{

    private final Map<String, Component> components = new HashMap<>();

    public Components(List<Component> components)
    {
        for (Component component : components)
            this.components.put(component.getDefinition().getIdentifier(), component);
    }

    public Component from(String identifier)
    {
        Component component = components.get(identifier);
        if (component == null)
            throw new UnknownComponentException();

        return component;
    }
}
