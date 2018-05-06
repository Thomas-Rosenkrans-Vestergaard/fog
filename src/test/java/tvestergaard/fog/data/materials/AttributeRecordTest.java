package tvestergaard.fog.data.materials;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class AttributeRecordTest
{

    @Test
    public void getName() throws Exception
    {
        String         expected  = randomString();
        AttributeValue attribute = new AttributeRecord(expected, DataType.INT, 1);
        assertEquals(expected, attribute.getName());
    }

    @Test
    public void getDataType() throws Exception
    {
        AttributeValue attribute;

        attribute = new AttributeRecord("name", DataType.INT, 0);
        assertEquals(DataType.INT, attribute.getDataType());
        attribute = new AttributeRecord("name", DataType.STRING, "");
        assertEquals(DataType.STRING, attribute.getDataType());
    }

    @Test
    public void setString() throws Exception
    {
        String         expected  = randomString();
        AttributeValue attribute = new AttributeRecord("name", DataType.STRING, "");
        attribute.setString(expected);
        assertEquals(expected, attribute.getString());
    }

    @Test
    public void setInt() throws Exception
    {
        int            expected  = randomInt(0, 10000);
        AttributeValue attribute = new AttributeRecord("name", DataType.INT, 1);
        attribute.setInt(expected);
        assertEquals(expected, attribute.getInt());
    }

    @Test
    public void getString() throws Exception
    {
        String         expected  = randomString();
        AttributeValue attribute = new AttributeRecord("name", DataType.STRING, "");
        attribute.setString(expected);
        assertEquals(expected, attribute.getString());
    }

    @Test
    public void getInt() throws Exception
    {
        int            expected  = randomInt(0, 10000);
        AttributeValue attribute = new AttributeRecord("name", DataType.INT, 1);
        attribute.setInt(expected);
        assertEquals(expected, attribute.getInt());
    }
}