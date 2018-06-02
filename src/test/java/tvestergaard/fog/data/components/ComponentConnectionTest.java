package tvestergaard.fog.data.components;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;

public class ComponentConnectionTest
{
    @Test
    public void from() throws Exception
    {

        int expectedDefinition = randomInt();
        int expectedMaterial   = randomInt();

        ComponentConnection instance = ComponentConnection.from(expectedDefinition, expectedMaterial);

        assertEquals(expectedDefinition, instance.getDefinitionId());
        assertEquals(expectedMaterial, instance.getMaterialId());
    }
}