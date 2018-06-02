package tvestergaard.fog.data.components;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class ComponentDefinitionBlueprintTest
{
    @Test
    public void from() throws Exception
    {
        String expectedIdentifier = randomString();
        String expectedNotes      = randomString();
        int    expectedCategory   = randomInt();

        ComponentDefinitionBlueprint instance = ComponentDefinitionBlueprint.from(expectedIdentifier, expectedNotes, expectedCategory);

        assertEquals(expectedIdentifier, instance.getIdentifier());
        assertEquals(expectedNotes, instance.getNotes());
        assertEquals(expectedCategory, instance.getCategoryId());
    }
}