package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.tokens.Token;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.email.ApplicationMailer;

import java.time.LocalDateTime;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;
import static tvestergaard.fog.data.tokens.Use.PASSWORD_RESET;

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
     * The token dao used to persist generated tokens.
     */
    private final TokenDAO tokenDAO;

    /**
     * The object responsible for sending the email to the customer.
     */
    private final ApplicationMailer mailer;

    /**
     * The token generator used to generate the password reset token sent by email to the customer.
     */
    private final TokenGenerator tokenGenerator;

    /**
     * Creates a new {@link PasswordResetter}.
     *
     * @param customerDAO    The customer dao used to access the customer data storage.
     * @param tokenDAO       The token dao used to persist generated tokens.
     * @param mailer         The object responsible for sending the email to the customer.
     * @param tokenGenerator The token generator used to generate the password reset token sent by email to the customer.
     */
    public PasswordResetter(CustomerDAO customerDAO, TokenDAO tokenDAO, ApplicationMailer mailer, TokenGenerator tokenGenerator)
    {
        this.customerDAO = customerDAO;
        this.tokenDAO = tokenDAO;
        this.mailer = mailer;
        this.tokenGenerator = tokenGenerator;
    }

    /**
     * Sends an email to the provided email address containing a link where the customer can reset their password.
     *
     * @param email The email address to send the password reset link to.
     * @throws DataAccessException   When a data storage exception occurs.
     * @throws UnknownEmailException When the provided email is not registered with the application.
     */
    public void send(String email) throws DataAccessException, UnknownEmailException
    {
        Customer customer = customerDAO.first(where(eq(EMAIL, email)));
        if (customer == null)
            throw new UnknownEmailException();

        String           token      = tokenGenerator.generate();
        Token            tokenDB    = tokenDAO.create(customer.getId(), hash(token), PASSWORD_RESET);
        ApplicationEmail resetEmail = new PasswordResetEmail(customer, tokenDB.getId(), token);
        mailer.send(resetEmail);
    }

    /**
     * Attempts to reset the password of some customer using the provided token and the new password.
     *
     * @param tokenId     The id of the token.
     * @param tokenSecret The secret token.
     * @param newPassword The new password.
     * @throws DataAccessException     When a data storage exception occurs.
     * @throws IncorrectTokenException When the provided token credentials are incorrect.
     * @throws ExpiredTokenException   When the provided token exists, but have expired.
     */
    public void reset(int tokenId, String tokenSecret, String newPassword) throws DataAccessException,
                                                                                  IncorrectTokenException,
                                                                                  ExpiredTokenException
    {
        // Retrieve token
        Token tokenDB = tokenDAO.get(tokenId);
        if (tokenDB == null)
            throw new IncorrectTokenException();

        // Check expiration
        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusHours(24));
        if (LocalDateTime.now().isAfter(expiration))
            throw new ExpiredTokenException();

        // Check secret
        if (!BCrypt.checkpw(tokenSecret, tokenDB.getHash()))
            throw new IncorrectTokenException();

        customerDAO.resetPassword(tokenId, hash(newPassword));
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
