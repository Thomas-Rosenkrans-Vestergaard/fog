package tvestergaard.fog.data.employees;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.*;

public class EmployeeRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        EmployeeRecord instance = new EmployeeRecord(expected, null, null, null, false, null, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getName() throws Exception
    {
        String         expected = randomString();
        EmployeeRecord instance = new EmployeeRecord(-1, expected, null, null, false, null, null);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void setName() throws Exception
    {
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, null, false, null, null);
        assertNull(instance.getName());
        String expected = randomString();
        instance.setName(expected);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void getUsername() throws Exception
    {
        String         expected = randomString();
        EmployeeRecord instance = new EmployeeRecord(-1, null, expected, null, false, null, null);
        assertEquals(expected, instance.getUsername());
    }

    @Test
    public void setUsername() throws Exception
    {
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, null, false, null, null);
        assertNull(instance.getUsername());
        String expected = randomString();
        instance.setUsername(expected);
        assertEquals(expected, instance.getUsername());
    }

    @Test
    public void getPassword() throws Exception
    {
        String         expected = randomString();
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, expected, false, null, null);
        assertEquals(expected, instance.getPassword());
    }

    @Test
    public void setPassword() throws Exception
    {
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, null, false, null, null);
        assertNull(instance.getPassword());
        String expected = randomString();
        instance.setPassword(expected);
        assertEquals(expected, instance.getPassword());
    }

    @Test
    public void isActive() throws Exception
    {
        boolean        expected = randomBoolean();
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, null, expected, null, null);
        assertEquals(expected, instance.isActive());
    }

    @Test
    public void setActive() throws Exception
    {
        boolean        expected = randomBoolean();
        EmployeeRecord instance = new EmployeeRecord(-1, null, null, null, expected, null, null);
        instance.setActive(!expected);
        assertEquals(!expected, instance.isActive());
    }

    @Test
    public void is() throws Exception
    {
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.HEAD_OF_CENTER);
        roles.add(Role.HEAD_OF_MATERIALS);

        EmployeeRecord employeeRecord = new EmployeeRecord(-1, null, null, null, false, roles, null);
        assertTrue(employeeRecord.is(Role.HEAD_OF_CENTER));
        assertTrue(employeeRecord.is(Role.HEAD_OF_MATERIALS));
        assertFalse(employeeRecord.is(Role.SALESMAN));
    }

    @Test
    public void remove() throws Exception
    {
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.HEAD_OF_CENTER);
        roles.add(Role.HEAD_OF_MATERIALS);

        EmployeeRecord employeeRecord = new EmployeeRecord(-1, null, null, null, false, roles, null);
        assertTrue(employeeRecord.is(Role.HEAD_OF_CENTER));
        assertTrue(employeeRecord.is(Role.HEAD_OF_MATERIALS));
        employeeRecord.remove(Role.HEAD_OF_CENTER);
        assertFalse(employeeRecord.is(Role.HEAD_OF_CENTER));
        assertTrue(employeeRecord.is(Role.HEAD_OF_MATERIALS));
    }

    @Test
    public void getRoles() throws Exception
    {
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.HEAD_OF_CENTER);
        roles.add(Role.HEAD_OF_MATERIALS);

        EmployeeRecord employeeRecord = new EmployeeRecord(-1, null, null, null, false, roles, null);
        assertEquals(roles, employeeRecord.getRoles());
    }

    @Test
    public void getCreatedAt() throws Exception
    {
        LocalDateTime  expected       = LocalDateTime.now();
        EmployeeRecord employeeRecord = new EmployeeRecord(-1, null, null, null, false, null, expected);
        assertEquals(expected, employeeRecord.getCreatedAt());
    }
}