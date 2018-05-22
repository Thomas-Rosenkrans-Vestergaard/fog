package tvestergaard.fog.logic.tokens;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.TokenRecord;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tvestergaard.fog.data.tokens.TokenUse.PASSWORD_RESET;

public class TokenAuthenticatorTest
{

    private TokenAuthenticator tokenAuthenticator;

    @Before
    public void before() throws Exception
    {
        Customer    customer = mock(Customer.class);
        TokenDAO    tokenDAO = mock(TokenDAO.class);
        TokenRecord result   = new TokenRecord(1, customer, BCrypt.hashpw("secret", BCrypt.gensalt()), PASSWORD_RESET, LocalDateTime.now());
        when(tokenDAO.get(1, PASSWORD_RESET)).thenReturn(result);
        tokenAuthenticator = new TokenAuthenticator(tokenDAO, 1);
    }

    @Test
    public void authenticate() throws Exception
    {
        assertTrue(tokenAuthenticator.authenticate(new TokenPair(1, "secret"), PASSWORD_RESET));
    }

    @Test(expected = ExpiredTokenException.class)
    public void authenticateThrowsExpiredTokenException() throws Exception
    {
        Thread.sleep(2 * 1000);
        tokenAuthenticator.authenticate(new TokenPair(1, "secret"), PASSWORD_RESET);
    }

    @Test
    public void authenticateFails() throws Exception
    {
        assertFalse(tokenAuthenticator.authenticate(new TokenPair(1, "wrong secret"), PASSWORD_RESET));
    }
}