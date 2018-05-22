package tvestergaard.fog.logic.customers;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;

import java.sql.Connection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.ID;

public class CustomerAuthenticationTest
{

    private CustomerDAO customerDAO = new MysqlCustomerDAO(TestDataSource.getSource());
    private CustomerAuthentication customerAuthentication;
    private String email    = randomString();
    private String password = randomString();

    @Before
    public void before() throws Exception
    {
        CustomerValidator customerValidator = mock(CustomerValidator.class);
        EmailVerifier     emailVerifier     = mock(EmailVerifier.class);

        when(customerValidator.validateRegister(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(new HashSet<>());
        when(customerValidator.validateUpdate(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(new HashSet<>());

        customerAuthentication = new CustomerAuthentication(customerDAO, customerValidator, emailVerifier);
    }

    @AfterClass
    public static void afterClass() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("DELETE FROM customers");
    }

    @Test
    public void register() throws Exception
    {
        String name    = randomString();
        String address = randomString();
        String phone   = randomString();

        Customer customer = customerAuthentication.register(name, address, email, phone, password);
        assertEquals(customer, customerDAO.first(where(eq(ID, customer.getId()))));
    }

    @Test
    public void authenticate() throws Exception
    {
        String name     = randomString();
        String address  = randomString();
        String email    = randomString();
        String phone    = randomString();
        String password = randomString();

        Customer customer = customerAuthentication.register(name, address, email, phone, password);
        assertEquals(customer, customerAuthentication.authenticate(email, password));
    }

    @Test(expected = InactiveCustomerException.class)
    public void authenticateThrowsInactiveCustomerException() throws Exception
    {
        String name     = randomString();
        String address  = randomString();
        String email    = randomString();
        String phone    = randomString();
        String password = randomString();

        Customer customer = customerAuthentication.register(name, address, email, phone, password);
        customer.setActive(false);
        customerDAO.update(customer);
        customerAuthentication.authenticate(email, password);
    }

    @Test(expected = CustomerAuthenticationException.class)
    public void authenticateThrowsCustomerAuthenticationException() throws Exception
    {
        customerAuthentication.authenticate("wrong_email", "wrong_password");
    }
}