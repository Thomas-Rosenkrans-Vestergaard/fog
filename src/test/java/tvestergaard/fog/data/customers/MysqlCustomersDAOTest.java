package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.constraints.OrderDirection;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlCustomersDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlCustomerDAO dao    = new MysqlCustomerDAO(source);

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;

    @Before
    public void createData() throws Exception
    {
        customer1 = dao.create(CustomerBlueprint.from("name1", "address1", "email1", "phone1", "password1", true));
        customer2 = dao.create(CustomerBlueprint.from("name2", "address2", "email2", "phone2", "password2", false));
        customer3 = dao.create(CustomerBlueprint.from("name3", "address3", "email3", "phone3", "password3", true));
        customer4 = dao.create(CustomerBlueprint.from("name4", "address4", "email4", "phone4", "password4", false));
        customer5 = dao.create(CustomerBlueprint.from("name5", "address5", "email5", "phone5", "password5", true));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM tokens");
        connection.createStatement().executeUpdate("DELETE FROM customers");
    }

    @Test
    public void get() throws Exception
    {
        List<Customer> customers = dao.get(null);

        assertEquals(5, customers.size());
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
        assertEquals(customer3, customers.get(2));
        assertEquals(customer4, customers.get(3));
        assertEquals(customer5, customers.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Customer> customers = dao.get(where(eq(CustomerColumn.ID, customer1.getId())));

        assertEquals(1, customers.size());
        assertEquals(customer1, customers.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Customer> customers = dao.get(where(like(CustomerColumn.NAME, "name%")));

        assertEquals(5, customers.size());
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
        assertEquals(customer3, customers.get(2));
        assertEquals(customer4, customers.get(3));
        assertEquals(customer5, customers.get(4));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Customer> customers = dao.get(order(CustomerColumn.NAME, OrderDirection.DESC));

        assertEquals(5, customers.size());
        assertEquals(customer5, customers.get(0));
        assertEquals(customer4, customers.get(1));
        assertEquals(customer3, customers.get(2));
        assertEquals(customer2, customers.get(3));
        assertEquals(customer1, customers.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Customer> customers = dao.get(limit(CustomerColumn.class, 2));

        assertEquals(2, customers.size());
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Customer> customers = dao.get(limit(CustomerColumn.class, 2).offset(1));

        assertEquals(customer2, customers.get(0));
        assertEquals(customer3, customers.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(customer3, dao.first(where(eq(ID, customer3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String  name     = randomString();
        String  address  = randomString();
        String  email    = randomString();
        String  phone    = randomString();
        String  password = randomString();
        boolean active   = randomBoolean();

        Customer actual = dao.create(CustomerBlueprint.from(name, address, email, phone, password, active));
        assertEquals(name, actual.getName());
        assertEquals(address, actual.getAddress());
        assertEquals(email, actual.getEmail());
        assertEquals(phone, actual.getPhone());
        assertEquals(password, actual.getPassword());
        assertEquals(active, actual.isActive());
        assertFalse(actual.isVerified());
    }

    @Test
    public void update() throws Exception
    {
        customer1.setName(randomString());
        customer1.setAddress(randomString());
        customer1.setEmail(randomString());
        customer1.setPhone(randomString());
        customer1.setPassword(randomString());
        customer1.setActive(false);

        assertTrue(dao.update(customer1));

        List<Customer> actual = dao.get(where(eq(ID, customer1.getId())));
        assertEquals(customer1, actual.get(0));
    }

    @Test
    public void activate() throws Exception
    {
        assertFalse(customer2.isActive());
        assertTrue(dao.activate(customer2.getId()));
        assertTrue(dao.first(where(eq(ID, customer2.getId()))).isActive());
    }

    @Test
    public void inactivate() throws Exception
    {
        assertTrue(customer1.isActive());
        assertTrue(dao.inactivate(customer1.getId()));
        assertFalse(dao.first(where(eq(ID, customer1.getId()))).isActive());
    }
}