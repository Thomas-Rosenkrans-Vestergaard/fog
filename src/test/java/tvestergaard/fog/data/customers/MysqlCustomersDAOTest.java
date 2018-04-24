package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlCustomersDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlCustomerDAO dao    = new MysqlCustomerDAO(source);

    @Before
    public void createData() throws Exception
    {
        Connection connection = source.getConnection();
        connection.createStatement().execute("DELETE FROM customers");
        connection.createStatement().execute(
                "INSERT INTO customers (id, name, address, email, phone, password, contact_method, active) " +
                        "VALUES (1, 'name1', 'address1', 'email1', 'phone1', 'password1', 0, b'1')");
        connection.createStatement().execute(
                "INSERT INTO customers (id, name, address, email, phone, password, contact_method, active) " +
                        "VALUES (2, 'name2', 'address2', 'email2', 'phone2', 'password2', 0, b'1')");
        connection.createStatement().execute(
                "INSERT INTO customers (id, name, address, email, phone, password, contact_method, active)" +
                        " VALUES (3, 'name3', 'address3', 'email3', 'phone3', 'password3', 0, b'1')");
        connection.createStatement().execute(
                "INSERT INTO customers (id, name, address, email, phone, password, contact_method, active)" +
                        " VALUES (4, 'name4', 'address4', 'email4', 'phone4', 'password4', 0, b'1')");
        connection.createStatement().execute(
                "INSERT INTO customers (id, name, address, email, phone, password, contact_method, active)" +
                        " VALUES (5, 'name5', 'address5', 'email5', 'phone5', 'password5', 0, b'0')");
    }

    @Test
    public void getAll() throws Exception
    {
        List<Customer> customers = dao.get();

        assertEquals(5, customers.size());
        assertEquals(1, customers.get(0).getId());
        assertEquals(2, customers.get(1).getId());
        assertEquals(3, customers.get(2).getId());
        assertEquals(4, customers.get(3).getId());
        assertEquals(5, customers.get(4).getId());
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Customer> customers = dao.get(where(eq("id", 1)));

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);
        assertEquals(1, customer.getId());
        assertEquals("name1", customer.getName());
        assertEquals("email1", customer.getEmail());
        assertEquals("phone1", customer.getPhone());
        assertEquals("password1", customer.getPassword());
        assertEquals(Customer.ContactMethod.EMAIL, customer.getContactMethod());
        assertEquals(true, customer.isActive());
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Customer> customers = dao.get(where(like("name", "name%")));

        assertEquals(5, customers.size());
        assertEquals("name1", customers.get(0).getName());
        assertEquals("name2", customers.get(1).getName());
        assertEquals("name3", customers.get(2).getName());
        assertEquals("name4", customers.get(3).getName());
        assertEquals("name5", customers.get(4).getName());
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Customer> customers = dao.get(order("name", desc()));

        assertEquals(5, customers.size());
        assertEquals("name5", customers.get(0).getName());
        assertEquals("name4", customers.get(1).getName());
        assertEquals("name3", customers.get(2).getName());
        assertEquals("name2", customers.get(3).getName());
        assertEquals("name1", customers.get(4).getName());
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Customer> customers = dao.get(limit(2));

        assertEquals(2, customers.size());
        assertEquals(1, customers.get(0).getId());
        assertEquals(2, customers.get(1).getId());
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Customer> customers = dao.get(limit(2), offset(1));

        assertEquals(2, customers.get(0).getId());
        assertEquals(3, customers.get(1).getId());
    }
}