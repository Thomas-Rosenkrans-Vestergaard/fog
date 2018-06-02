package tvestergaard.fog.data.customers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class CustomerUpdaterTest
{

    @Test
    public void from() throws Exception
    {
        int     expectedId       = randomInt();
        String  expectedName     = randomString();
        String  expectedAddress  = randomString();
        String  expectedEmail    = randomString();
        String  expectedPhone    = randomString();
        String  expectedPassword = randomString();
        boolean expectedActive   = randomBoolean();
        boolean expectedVerified = randomBoolean();

        CustomerUpdater instance = CustomerUpdater.from(expectedId, expectedName, expectedAddress, expectedEmail,
                expectedPhone, expectedPassword, expectedActive, expectedVerified);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedName, instance.getName());
        assertEquals(expectedAddress, instance.getAddress());
        assertEquals(expectedEmail, instance.getEmail());
        assertEquals(expectedPhone, instance.getPhone());
        assertEquals(expectedPassword, instance.getPassword());
        assertEquals(expectedActive, instance.isActive());
        assertEquals(expectedVerified, instance.isVerified());
    }
}