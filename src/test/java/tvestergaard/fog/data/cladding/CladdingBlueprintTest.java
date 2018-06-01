package tvestergaard.fog.data.cladding;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;

public class CladdingBlueprintTest
{
    @Test
    public void from() throws Exception
    {

        String  expectedName        = randomString();
        String  expectedDescription = randomString();
        boolean expectedActive      = randomBoolean();

        CladdingBlueprint instance = CladdingBlueprint.from(expectedName, expectedDescription, expectedActive);

        assertEquals(expectedName, instance.getName());
        assertEquals(expectedDescription, instance.getDescription());
        assertEquals(expectedActive, instance.isActive());
    }
}