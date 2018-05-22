package tvestergaard.fog.data.materials;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class CategoryRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        CategoryRecord instance = new CategoryRecord(expected, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getName() throws Exception
    {
        String         expected = randomString();
        CategoryRecord instance = new CategoryRecord(-1, expected);
        assertEquals(expected, instance.getName());
    }
}