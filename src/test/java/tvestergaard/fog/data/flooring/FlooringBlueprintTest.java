package tvestergaard.fog.data.flooring;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;

public class FlooringBlueprintTest
{

    @Test
    public void from() throws Exception
    {
        String  expectedName        = randomString();
        String  expectedDescription = randomString();
        boolean expectedActive      = randomBoolean();

        FlooringBlueprint instance = FlooringBlueprint.from(expectedName, expectedDescription, expectedActive);

        assertEquals(expectedName, instance.getName());
        assertEquals(expectedDescription, instance.getDescription());
        assertEquals(expectedActive, instance.isActive());
    }
}