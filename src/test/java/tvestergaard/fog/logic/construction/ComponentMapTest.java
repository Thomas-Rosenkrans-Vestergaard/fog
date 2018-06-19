package tvestergaard.fog.logic.construction;

import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinitionRecord;
import tvestergaard.fog.data.components.ComponentRecord;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComponentMapTest
{

    private List<Component> components;
    private ComponentMap    componentMap;

    @Before
    public void before() throws Exception
    {
        components = new ArrayList<>();
        components.add(new ComponentRecord(-1, -1, new ComponentDefinitionRecord(-1, "IDENTIFIER", "", 1, null, false), new ArrayList<>()));

        componentMap = new ComponentMap(components);
    }

    @Test
    public void from() throws Exception
    {
        assertEquals(components.get(0), componentMap.from("IDENTIFIER"));
    }

    @Test(expected = UnknownComponentException.class)
    public void fromThrowsUnknownComponentExcepetion() throws Exception
    {
        componentMap.from("UNKNOWN");
    }
}