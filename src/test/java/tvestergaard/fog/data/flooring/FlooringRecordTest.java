package tvestergaard.fog.data.flooring;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class FlooringRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        FlooringRecord instance = new FlooringRecord(expected, null, null, false);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getName() throws Exception
    {
        String         expected = randomString();
        FlooringRecord instance = new FlooringRecord(-1, expected, null, false);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void setName() throws Exception
    {
        FlooringRecord instance = new FlooringRecord(-1, null, null, false);
        assertNull(instance.getName());
        String expected = randomString();
        instance.setName(expected);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void getDescription() throws Exception
    {
        String         expected = randomString();
        FlooringRecord instance = new FlooringRecord(-1, null, expected, false);
        assertEquals(expected, instance.getDescription());
    }

    @Test
    public void setDescription() throws Exception
    {
        FlooringRecord instance = new FlooringRecord(-1, null, null, false);
        assertNull(instance.getName());
        String expected = randomString();
        instance.setDescription(expected);
        assertEquals(expected, instance.getDescription());
    }

    @Test
    public void isActive() throws Exception
    {
        boolean        expected = randomBoolean();
        FlooringRecord instance = new FlooringRecord(-1, null, null, expected);
        assertEquals(expected, instance.isActive());
    }

    @Test
    public void setActive() throws Exception
    {
        boolean        expected = randomBoolean();
        FlooringRecord instance = new FlooringRecord(-1, null, null, expected);
        assertEquals(expected, instance.isActive());
        instance.setActive(!expected);
        assertEquals(!expected, instance.isActive());
    }
}