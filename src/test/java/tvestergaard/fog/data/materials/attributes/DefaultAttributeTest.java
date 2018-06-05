package tvestergaard.fog.data.materials.attributes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;

public class DefaultAttributeTest
{

    @Test(expected = IncorrectDataTypeException.class)
    public void constructorThrowsIncorrectDataTypeExceptionOnString() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
    }

    @Test(expected = IncorrectDataTypeException.class)
    public void constructorThrowsIncorrectDataTypeExceptionOnInt() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomString());
    }

    public void constructorDoesNotThrowIncorrectDataTypeException() throws Exception
    {
        AttributeDefinition definitionOne = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        AttributeDefinition definitionTwo = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);

        new DefaultAttribute(definitionOne, randomInt());
        new DefaultAttribute(definitionTwo, randomString());
    }

    @Test
    public void getDefinition() throws Exception
    {
        AttributeDefinition definition = mock(AttributeDefinition.class);
        DefaultAttribute    instance   = new DefaultAttribute(definition, null);
        assertEquals(definition, instance.getDefinition());
    }

    @Test
    public void getValue() throws Exception
    {
        String              expected   = randomString();
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, expected);
        assertEquals(expected, instance.getValue());
    }

    @Test
    public void setString() throws Exception
    {
        String              expected   = randomString();
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, "");
        instance.setString(expected);
        assertEquals(expected, instance.getValue());
    }

    @Test(expected = IncorrectDataTypeException.class)
    public void setStringThrowsIncorrectDataTypeException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
        instance.setString(randomString());
    }

    @Test
    public void setInt() throws Exception
    {
        Integer             expected   = randomInt();
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
        instance.setInt(expected);
        assertEquals(expected, instance.getValue());
    }

    @Test(expected = IncorrectDataTypeException.class)
    public void setIntThrowsIncorrectDataTypeException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
        instance.setInt(randomInt());
    }

    @Test
    public void getString() throws Exception
    {
        String              expected   = randomString();
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, "");
        instance.setString(expected);
        assertEquals(expected, instance.getString());
    }

    @Test(expected = AttributeValueNullException.class)
    public void getStringThrowsAttributeValueNullException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, null);
        instance.getString();
    }

    @Test(expected = IncorrectDataTypeException.class)
    public void getStringThrowsIncorrectDataTypeException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
        instance.getString();
    }

    @Test
    public void getInt() throws Exception
    {
        Integer             expected   = randomInt();
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomInt());
        instance.setInt(expected);
        assertEquals(expected, instance.getInt());
    }

    @Test(expected = AttributeValueNullException.class)
    public void getIntThrowsAttributeValueNullException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.INT);
        DefaultAttribute    instance   = new DefaultAttribute(definition, null);
        instance.getInt();
    }

    @Test(expected = IncorrectDataTypeException.class)
    public void getIntThrowsIncorrectDataTypeException() throws Exception
    {
        AttributeDefinition definition = new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING);
        DefaultAttribute    instance   = new DefaultAttribute(definition, randomString());
        instance.getInt();
    }

    @Test
    public void equals() throws Exception
    {
        Integer expectedInt    = randomInt();
        String  expectedString = randomString();

        AttributeDefinition definitionOne = new DefaultAttributeDefinition(expectedInt, expectedString, DataType.INT);
        AttributeDefinition definitionTwo = new DefaultAttributeDefinition(expectedInt, expectedString, DataType.STRING);
        DefaultAttribute    instanceOne   = new DefaultAttribute(definitionOne, expectedInt);
        DefaultAttribute    instanceTwo   = new DefaultAttribute(definitionOne, expectedInt);
        DefaultAttribute    instanceThree = new DefaultAttribute(definitionTwo, expectedString);
        DefaultAttribute    instanceFour  = new DefaultAttribute(definitionOne, null);
        DefaultAttribute    instanceFive  = new DefaultAttribute(definitionOne, 12);

        assertEquals(instanceOne, instanceTwo);
        assertNotEquals(instanceOne, instanceFour);
        assertNotEquals(instanceOne, instanceFive);
        assertNotEquals(instanceFour, instanceFive);
        assertNotEquals(instanceTwo, instanceThree);
    }
}