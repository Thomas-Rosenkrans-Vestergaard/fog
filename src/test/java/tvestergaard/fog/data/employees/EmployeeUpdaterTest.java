package tvestergaard.fog.data.employees;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.*;

public class EmployeeUpdaterTest
{

    @Test
    public void from() throws Exception
    {
        int       expectedId       = randomInt();
        String    expectedName     = randomString();
        String    expectedUsername = randomString();
        String    expectedPassword = randomString();
        Set<Role> expectedRoles    = new HashSet<>();
        Role      role             = randomEnum(Role.class);
        expectedRoles.add(role);
        boolean expectedActive = randomBoolean();

        EmployeeUpdater instance = EmployeeUpdater.from(expectedId, expectedName, expectedUsername, expectedPassword, expectedRoles, expectedActive);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedName, instance.getName());
        assertEquals(expectedUsername, instance.getUsername());
        assertEquals(expectedPassword, instance.getPassword());
        assertEquals(expectedRoles, instance.getRoles());
        assertTrue(instance.is(role));
        assertEquals(expectedActive, instance.isActive());
    }
}