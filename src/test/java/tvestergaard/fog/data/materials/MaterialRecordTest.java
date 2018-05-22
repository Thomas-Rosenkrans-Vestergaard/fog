package tvestergaard.fog.data.materials;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class MaterialRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(expected, null, null, -1, -1, false, -1, null, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getNumber() throws Exception
    {
        String         expected = randomString();
        MaterialRecord instance = new MaterialRecord(-1, expected, null, -1, -1, false, -1, null, null);
        assertEquals(expected, instance.getNumber());
    }

    @Test
    public void getDescription() throws Exception
    {
        String         expected = randomString();
        MaterialRecord instance = new MaterialRecord(-1, null, expected, -1, -1, false, -1, null, null);
        assertEquals(expected, instance.getDescription());
    }

    @Test
    public void setDescription() throws Exception
    {
    }

    @Test
    public void getPrice() throws Exception
    {
    }

    @Test
    public void setPrice() throws Exception
    {
    }

    @Test
    public void getUnit() throws Exception
    {
    }

    @Test
    public void setUnit() throws Exception
    {
    }

    @Test
    public void isActive() throws Exception
    {
    }

    @Test
    public void getCategoryId() throws Exception
    {
    }

    @Test
    public void getCategory() throws Exception
    {
    }

    @Test
    public void getAttributes() throws Exception
    {
    }

    @Test
    public void getAttribute() throws Exception
    {
    }
}