package tvestergaard.fog.data.materials.attributes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static tvestergaard.fog.Helpers.*;

public class DefaultAttributeDefinitionTest
{

    @Test
    public void getId() throws Exception
    {
        int                        expected = randomInt();
        DefaultAttributeDefinition instance = new DefaultAttributeDefinition(expected, randomString(), DataType.INT);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getName() throws Exception
    {
        String                     expected = randomString();
        DefaultAttributeDefinition instance = new DefaultAttributeDefinition(randomInt(), expected, DataType.INT);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void getDataType() throws Exception
    {
        DataType                   expected = randomEnum(DataType.class);
        DefaultAttributeDefinition instance = new DefaultAttributeDefinition(randomInt(), randomString(), expected);
        assertEquals(expected, instance.getDataType());
    }

    @Test
    public void equals() throws Exception
    {
        int    expectedInt  = randomInt();
        String expectedName = randomString();

        DefaultAttributeDefinition instanceOne   = new DefaultAttributeDefinition(expectedInt, expectedName, DataType.INT);
        DefaultAttributeDefinition instanceTwo   = new DefaultAttributeDefinition(expectedInt, expectedName, DataType.INT);
        DefaultAttributeDefinition instanceThree = new DefaultAttributeDefinition(expectedInt, expectedName, DataType.STRING);
        DefaultAttributeDefinition instanceFour  = new DefaultAttributeDefinition(expectedInt, expectedName, null);
        DefaultAttributeDefinition instanceFive  = new DefaultAttributeDefinition(0, "", null);

        assertEquals(instanceOne, instanceTwo);
        assertNotEquals(instanceOne, instanceThree);
        assertNotEquals(instanceThree, instanceFour);
        assertNotEquals(instanceFour, instanceFive);
    }
}