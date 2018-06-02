package tvestergaard.fog.data.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class ModelUpdaterTest
{
    @Test
    public void from() throws Exception
    {
        int    expectedId   = randomInt();
        String expectedName = randomString();

        ModelUpdater instance = ModelUpdater.from(expectedId, expectedName);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedName, instance.getName());
    }
}