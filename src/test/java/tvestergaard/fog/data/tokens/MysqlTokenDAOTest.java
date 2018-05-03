package tvestergaard.fog.data.tokens;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;

public class MysqlTokenDAOTest
{

    private final CustomerDAO customerDAO = new MysqlCustomerDAO(TestDataSource.getSource());
    private final TokenDAO    tokenDAO    = new MysqlTokenDAO(TestDataSource.getSource());

    private Customer customer1;
    private Customer customer2;
    private Token    token1;
    private Token    token2;

    @Before
    public void before() throws Exception
    {
        customer1 = customerDAO.create(CustomerBlueprint.from("name1", "address1", "email1", "phone1", "password1", true));
        customer2 = customerDAO.create(CustomerBlueprint.from("name2", "address2", "email2", "phone2", "password2", false));

        token1 = tokenDAO.create(customer1.getId(), "secret1", TokenUse.EMAIL_VERIFICATION);
        token2 = tokenDAO.create(customer2.getId(), "secret2", TokenUse.PASSWORD_RESET);
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM tokens");
        connection.createStatement().executeUpdate("DELETE FROM customers");
    }

    @Test
    public void create() throws Exception
    {
        Customer customer = customerDAO.create(CustomerBlueprint.from(
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomBoolean()));

        int    expectedCustomerID = customer.getId();
        String expectedSecret     = randomString();
        Token  token              = tokenDAO.create(expectedCustomerID, expectedSecret, TokenUse.PASSWORD_RESET);
        assertEquals(customer, token.getCustomer());
        assertEquals(expectedSecret, token.getHash());
        assertEquals(TokenUse.PASSWORD_RESET, token.getUse());
    }

    @Test
    public void get() throws Exception
    {
        assertNull(tokenDAO.get(-1, TokenUse.PASSWORD_RESET));
        assertEquals(token1, tokenDAO.get(token1.getId(), token1.getUse()));
        assertEquals(token2, tokenDAO.get(token2.getId(), token2.getUse()));
    }
}