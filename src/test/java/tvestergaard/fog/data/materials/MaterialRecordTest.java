package tvestergaard.fog.data.materials;

import org.junit.Test;
import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.attributes.DataType;
import tvestergaard.fog.data.materials.attributes.DefaultAttribute;
import tvestergaard.fog.data.materials.attributes.DefaultAttributeDefinition;
import tvestergaard.fog.data.materials.categories.Category;
import tvestergaard.fog.data.materials.categories.CategoryRecord;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class MaterialRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(expected, null, null, -1, -1, false, -1, null, new HashSet<>());
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getNumber() throws Exception
    {
        String         expected = randomString();
        MaterialRecord instance = new MaterialRecord(-1, expected, null, -1, -1, false, -1, null, new HashSet<>());
        assertEquals(expected, instance.getNumber());
    }

    @Test
    public void getDescription() throws Exception
    {
        String         expected = randomString();
        MaterialRecord instance = new MaterialRecord(-1, null, expected, -1, -1, false, -1, null, new HashSet<>());
        assertEquals(expected, instance.getDescription());
    }

    @Test
    public void setDescription() throws Exception
    {
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, null, new HashSet<>());
        assertNull(instance.getDescription());
        String expected = randomString();
        instance.setDescription(expected);
        assertEquals(expected, instance.getDescription());
    }

    @Test
    public void getPrice() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(-1, null, null, expected, -1, false, -1, null, new HashSet<>());
        assertEquals(expected, instance.getPrice());
    }

    @Test
    public void setPrice() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, null, new HashSet<>());
        assertEquals(-1, instance.getPrice());
        instance.setPrice(expected);
        assertEquals(expected, instance.getPrice());
    }

    @Test
    public void getUnit() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, expected, false, -1, null, new HashSet<>());
        assertEquals(expected, instance.getUnit());
    }

    @Test
    public void setUnit() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, null, new HashSet<>());
        assertEquals(-1, instance.getUnit());
        instance.setUnit(expected);
        assertEquals(expected, instance.getUnit());
    }

    @Test
    public void isActive() throws Exception
    {
        boolean        expected = randomBoolean();
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, expected, -1, null, new HashSet<>());
        assertEquals(expected, instance.isActive());
    }

    @Test
    public void getCategoryId() throws Exception
    {
        int            expected = randomInt();
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, expected, null, new HashSet<>());
        assertEquals(expected, instance.getCategoryId());
    }

    @Test
    public void getCategory() throws Exception
    {
        Category       expected = new CategoryRecord(randomInt(), randomString());
        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, expected, new HashSet<>());
        assertEquals(expected, instance.getCategory());
    }

    @Test
    public void getAttributes() throws Exception
    {
        Set<Attribute> attributes = new HashSet<>();

        attributes.add(new DefaultAttribute(new DefaultAttributeDefinition(1, "ATTR_A", DataType.INT), 1));
        attributes.add(new DefaultAttribute(new DefaultAttributeDefinition(2, "ATTR_B", DataType.INT), 2));

        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, null, attributes);
        assertEquals(attributes, instance.getAttributes());
    }

    @Test
    public void getAttribute() throws Exception
    {
        Set<Attribute> attributes = new HashSet<>();

        Attribute expected1 = new DefaultAttribute(
                new DefaultAttributeDefinition(-1, "ATTRIBUTE_ONE", DataType.INT),
                randomInt());

        Attribute expected2 = new DefaultAttribute(
                new DefaultAttributeDefinition(-1, "ATTRIBUTE_TWO", DataType.INT),
                randomInt());

        attributes.add(expected1);
        attributes.add(expected2);

        MaterialRecord instance = new MaterialRecord(-1, null, null, -1, -1, false, -1, null, attributes);
        assertEquals(attributes, instance.getAttributes());

        assertEquals(expected1, instance.getAttribute("ATTRIBUTE_ONE"));
        assertEquals(expected2, instance.getAttribute("ATTRIBUTE_TWO"));
        assertNull(instance.getAttribute("ATTRIBUTE_THREE")); // not known
    }
}