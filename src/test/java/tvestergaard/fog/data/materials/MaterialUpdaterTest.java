package tvestergaard.fog.data.materials;

import org.junit.Test;
import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.attributes.DataType;
import tvestergaard.fog.data.materials.attributes.DefaultAttribute;
import tvestergaard.fog.data.materials.attributes.DefaultAttributeDefinition;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class MaterialUpdaterTest
{
    @Test
    public void from() throws Exception
    {
        int            expectedId          = randomInt();
        String         expectedNumber      = randomString();
        String         expectedDescription = randomString();
        int            expectedPrice       = randomInt();
        int            expectedUnit        = randomInt();
        int            expectedCategory    = randomInt();
        Set<Attribute> expectedAttributes  = new HashSet<>();
        expectedAttributes.add(new DefaultAttribute(new DefaultAttributeDefinition(randomInt(), randomString(), DataType.STRING), randomString()));

        MaterialUpdater instance = MaterialUpdater.from(expectedId, expectedNumber, expectedDescription, expectedPrice,
                expectedUnit, expectedCategory, expectedAttributes);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedNumber, instance.getNumber());
        assertEquals(expectedDescription, instance.getDescription());
        assertEquals(expectedPrice, instance.getPrice());
        assertEquals(expectedUnit, instance.getUnit());
        assertEquals(expectedCategory, instance.getCategoryId());
        assertEquals(expectedAttributes, instance.getAttributes());
    }
}