package tvestergaard.fog.data.customers;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.*;

public class CustomerRecordTest
{
    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt();
        CustomerRecord instance = new CustomerRecord(expected, null, null, null, null, null, false, false, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getName() throws Exception
    {
        String         expected = randomString();
        CustomerRecord instance = new CustomerRecord(-1, expected, null, null, null, null, false, false, null);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void setName() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertNull(instance.getName());
        String expected = randomString();
        instance.setName(expected);
        assertEquals(expected, instance.getName());
    }

    @Test
    public void getAddress() throws Exception
    {
        String         expected = randomString();
        CustomerRecord instance = new CustomerRecord(-1, null, expected, null, null, null, false, false, null);
        assertEquals(expected, instance.getAddress());
    }

    @Test
    public void setAddress() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertNull(instance.getAddress());
        String expected = randomString();
        instance.setAddress(expected);
        assertEquals(expected, instance.getAddress());
    }

    @Test
    public void getEmail() throws Exception
    {
        String         expected = randomString();
        CustomerRecord instance = new CustomerRecord(-1, null, null, expected, null, null, false, false, null);
        assertEquals(expected, instance.getEmail());
    }

    @Test
    public void setEmail() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertNull(instance.getEmail());
        String expected = randomString();
        instance.setEmail(expected);
        assertEquals(expected, instance.getEmail());
    }

    @Test
    public void getPhone() throws Exception
    {
        String         expected = randomString();
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, expected, null, false, false, null);
        assertEquals(expected, instance.getPhone());
    }

    @Test
    public void setPhone() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertNull(instance.getPhone());
        String expected = randomString();
        instance.setPhone(expected);
        assertEquals(expected, instance.getPhone());
    }

    @Test
    public void getPassword() throws Exception
    {
        String         expected = randomString();
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, expected, false, false, null);
        assertEquals(expected, instance.getPassword());
    }

    @Test
    public void setPassword() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertNull(instance.getPassword());
        String expected = randomString();
        instance.setPassword(expected);
        assertEquals(expected, instance.getPassword());
    }

    @Test
    public void isActive() throws Exception
    {
        boolean        expected = randomBoolean();
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, expected, false, null);
        assertEquals(expected, instance.isActive());
    }

    @Test
    public void setActive() throws Exception
    {
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, null);
        assertFalse(instance.isActive());
        boolean expected = randomBoolean();
        instance.setActive(expected);
        assertEquals(expected, instance.isActive());
    }

    @Test
    public void isVerified() throws Exception
    {
        boolean        expected = randomBoolean();
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, expected, null);
        assertEquals(expected, instance.isVerified());
    }

    @Test
    public void getCreatedAt() throws Exception
    {
        LocalDateTime  expected = LocalDateTime.now();
        CustomerRecord instance = new CustomerRecord(-1, null, null, null, null, null, false, false, expected);
        assertEquals(expected, instance.getCreatedAt());
    }
}