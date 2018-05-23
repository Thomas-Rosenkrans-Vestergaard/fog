package tvestergaard.fog.logic.tokens;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Matchers;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.TokenRecord;
import tvestergaard.fog.data.tokens.TokenUse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TokenIssuerTest
{

    private TokenDAO       tokenDAO;
    private TokenGenerator tokenGenerator;
    private TokenIssuer    instance;

    @Before
    public void before() throws Exception
    {
        tokenDAO = mock(TokenDAO.class);
        tokenGenerator = mock(TokenGenerator.class);
        when(tokenGenerator.generate()).thenReturn("secret");
        when(tokenDAO.create(anyInt(), anyString(), any())).thenReturn(new TokenRecord(-1, mock(Customer.class), null, null, null));
        instance = new TokenIssuer(tokenDAO, tokenGenerator);
    }

    @Test
    public void issue() throws Exception
    {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(5);

        TokenPair result = instance.issue(customer, TokenUse.EMAIL_VERIFICATION);

        verify(tokenDAO, times(1)).create(eq(5), Matchers.argThat(new Matcher("secret")), eq(TokenUse.EMAIL_VERIFICATION));
        verify(tokenGenerator, times(1)).generate();
        assertEquals("secret", result.secret);
    }

    private class Matcher extends BaseMatcher<String>
    {

        private final String plaintext;

        public Matcher(String plaintext)
        {
            this.plaintext = plaintext;
        }

        @Override public boolean matches(Object o)
        {
            return BCrypt.checkpw(plaintext, (String) o);
        }

        @Override public void describeTo(Description description)
        {
            description.appendText("Checks that the hash argument matches the plaintext.");
        }
    }
}