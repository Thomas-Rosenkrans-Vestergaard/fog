package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.tokens.*;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;
import static tvestergaard.fog.data.tokens.TokenUse.PASSWORD_RESET;

/**
 * Sends an email to the provided customer, so that the customer can reset their password.
 */
public class PasswordResetter
{

    /**
     * The customer dao used to access the customer data storage.
     */
    private final CustomerDAO customerDAO;

    /**
     * The object used to issue the tokens used by customers when resetting their password.
     */
    private final TokenIssuer tokenIssuer;

    /**
     * The object used to authenticate tokens used by customers when resetting their password.
     */
    private final TokenAuthenticator tokenAuthenticator;

    /**
     * The object responsible for sending the email to the customer.
     */
    private final ApplicationMailer mailer;

    /**
     * Creates a new {@link PasswordResetter}.
     *
     * @param customerDAO        The customer dao used to access the customer data storage.
     * @param tokenIssuer        The object used to issue the tokens used by customers when resetting their password.
     * @param tokenAuthenticator The object used to authenticate tokens used by customers when resetting their password.
     * @param mailer             The object responsible for sending the email to the customer.
     */
    public PasswordResetter(CustomerDAO customerDAO, TokenIssuer tokenIssuer, TokenAuthenticator tokenAuthenticator, ApplicationMailer mailer)
    {
        this.customerDAO = customerDAO;
        this.tokenIssuer = tokenIssuer;
        this.tokenAuthenticator = tokenAuthenticator;
        this.mailer = mailer;
    }

    /**
     * Sends an email to the provided email address containing a link where the customer can reset their password.
     *
     * @param email The email address to send the password reset link to.
     * @throws DataAccessException       When a data storage exception occurs.
     * @throws UnknownEmailException     When the provided email is not registered with the application.
     * @throws InactiveCustomerException When the customer is inactive.
     */
    public void send(String email) throws DataAccessException, UnknownEmailException, InactiveCustomerException
    {
        Customer customer = customerDAO.first(where(eq(EMAIL, email)));
        if (customer == null)
            throw new UnknownEmailException();

        if (!customer.isActive())
            throw new InactiveCustomerException();

        TokenSecret      secret     = tokenIssuer.issue(customer, PASSWORD_RESET);
        ApplicationEmail resetEmail = new PasswordResetEmail(customer, secret);
        mailer.send(resetEmail);
    }

    /**
     * Attempts to reset the password of some customer using the provided token and the new password.
     *
     * @param tokenSecret The secret token to validate.
     * @param newPassword The new password.
     * @throws DataAccessException     When a data storage exception occurs.
     * @throws IncorrectTokenException When the provided token credentials are incorrect.
     * @throws ExpiredTokenException   When the provided token exists, but have expired.
     */
    public void reset(TokenSecret tokenSecret, String newPassword) throws DataAccessException,
                                                                          IncorrectTokenException,
                                                                          ExpiredTokenException
    {
        if (!tokenAuthenticator.authenticate(tokenSecret, PASSWORD_RESET))
            throw new IncorrectTokenException();

        customerDAO.resetPassword(tokenSecret.id, hash(newPassword));
    }

    /**
     * Hashes the provided token using the b-crypt algorithm.
     *
     * @param password The password to hash.
     * @return The resulting digest.
     */
    public static String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
