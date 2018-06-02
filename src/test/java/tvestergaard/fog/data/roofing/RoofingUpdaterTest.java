package tvestergaard.fog.data.roofing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class RoofingUpdaterTest
{

    @Test
    public void from() throws Exception
    {
        int     expectedId          = randomInt();
        String  expectedName        = randomString();
        String  expectedDescription = randomString();
        boolean expectedActive      = randomBoolean();

        RoofingUpdater instance = RoofingUpdater.from(expectedId, expectedName, expectedDescription, expectedActive);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedName, instance.getName());
        assertEquals(expectedDescription, instance.getDescription());
        assertEquals(expectedActive, instance.isActive());
    }
}