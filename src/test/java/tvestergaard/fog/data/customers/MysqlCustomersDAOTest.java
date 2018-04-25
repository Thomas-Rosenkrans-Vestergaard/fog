package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
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
        Connection connection = source.getConnection();
        connection.createStatement().execute("DELETE FROM customers");
        customer1 = dao.create("name1", "address1", "email1", "phone1", "password1", Customer.ContactMethod.EMAIL, true);
        customer2 = dao.create("name2", "address2", "email2", "phone2", "password2", Customer.ContactMethod.PHONE, false);
        customer3 = dao.create("name3", "address3", "email3", "phone3", "password3", Customer.ContactMethod.EMAIL, true);
        customer4 = dao.create("name4", "address4", "email4", "phone4", "password4", Customer.ContactMethod.PHONE, false);
        customer5 = dao.create("name5", "address5", "email5", "phone5", "password5", Customer.ContactMethod.EMAIL, true);
    }

    @Test
    public void get() throws Exception
    {
        List<Customer> customers = dao.get();

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
        List<Customer> customers = dao.get(order(CustomerColumn.NAME, desc()));

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
        List<Customer> customers = dao.get(limit(2));

        assertEquals(2, customers.size());
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Customer> customers = dao.get(limit(2), offset(1));

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
        String                 name          = "some_random_name";
        String                 address       = "some_random_address";
        String                 email         = "some_random_email";
        String                 phone         = "some_random_phone";
        String                 password      = "some_random_password";
        Customer.ContactMethod contactMethod = Customer.ContactMethod.PHONE;
        boolean                active        = false;

        Customer actual = dao.create(name, address, email, phone, password, contactMethod, active);
        assertEquals(name, actual.getName());
        assertEquals(address, actual.getAddress());
        assertEquals(email, actual.getEmail());
        assertEquals(phone, actual.getPhone());
        assertEquals(password, actual.getPassword());
        assertEquals(contactMethod, actual.getContactMethod());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        customer1.setName("new_name");
        customer1.setAddress("new_address");
        customer1.setEmail("new_email");
        customer1.setPhone("new_phone");
        customer1.setPassword("new_password");
        customer1.setContactMethod(Customer.ContactMethod.PHONE);
        customer1.setActive(false);

        assertTrue(dao.update(customer1));

        List<Customer> actual = dao.get(where(eq(ID, customer1.getId())));
        assertEquals(customer1, actual.get(0));
    }
}