package tvestergaard.fog.data.customers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;

public class CustomerBlueprintTest
{

    @Test
    public void from() throws Exception
    {
        String  expectedName     = randomString();
        String  expectedAddress  = randomString();
        String  expectedEmail    = randomString();
        String  expectedPhone    = randomString();
        String  expectedPassword = randomString();
        boolean expectedActive   = randomBoolean();

        CustomerBlueprint instance = CustomerBlueprint.from(expectedName, expectedAddress, expectedEmail, expectedPhone, expectedPassword, expectedActive);

        assertEquals(expectedName, instance.getName());
        assertEquals(expectedAddress, instance.getAddress());
        assertEquals(expectedEmail, instance.getEmail());
        assertEquals(expectedPhone, instance.getPhone());
        assertEquals(expectedPassword, instance.getPassword());
        assertEquals(expectedActive, instance.isActive());
    }
}