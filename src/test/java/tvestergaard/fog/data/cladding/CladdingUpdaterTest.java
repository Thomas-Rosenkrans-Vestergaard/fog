package tvestergaard.fog.data.cladding;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class CladdingUpdaterTest
{

    @Test
    public void from() throws Exception
    {

        int     expectedId          = randomInt();
        String  expectedName        = randomString();
        String  expectedDescription = randomString();
        boolean expectedActive      = randomBoolean();

        CladdingUpdater instance = CladdingUpdater.from(expectedId, expectedName, expectedDescription, expectedActive);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedName, instance.getName());
        assertEquals(expectedDescription, instance.getDescription());
        assertEquals(expectedActive, instance.isActive());
    }
}