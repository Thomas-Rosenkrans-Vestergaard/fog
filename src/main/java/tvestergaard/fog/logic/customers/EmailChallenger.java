package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.tokens.Token;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.Use;
import tvestergaard.fog.logic.email.ApplicationMailer;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * Challenges new customers to verify their email address.
 */
public class EmailChallenger
{

    /**
     * The source of randomness used to generate the tokens.
     */
    private final SecureRandom random = new SecureRandom();

    /**
     * The customer DAO used to
     */
    private final CustomerDAO customerDAO;

    /**
     * The DAO used to update the customer in data storage.
     */
    private final TokenDAO          tokenDAO;
    private final ApplicationMailer mailer;

    /**
     * The number of hours the token can be used from when it was first issued.
     */
    private final int EXPIRATION_TIME_HOURS = 24;

    public EmailChallenger(CustomerDAO customerDAO, TokenDAO tokenDAO, ApplicationMailer mailer)
    {
        this.customerDAO = customerDAO;
        this.tokenDAO = tokenDAO;
        this.mailer = mailer;
    }

    /**
     * Sends a registration confirmation email to the provided customer.
     *
     * @param customer The customer to send the registration confirmation email to.
     * @return {@code true} if the registration confirmation email was successfully sent.
     * @throws DataAccessException
     */
    public void challenge(Customer customer) throws DataAccessException
    {
        String            token   = new TokenGenerator().generate();
        Token             tokenDB = tokenDAO.create(customer.getId(), hash(token), Use.EMAIL_CHALLENGE);
        RegistrationEmail email   = new RegistrationEmail(customer, tokenDB.getId(), token);
        mailer.send(email);
    }

    private String hash(String token)
    {
        return BCrypt.hashpw(token, BCrypt.gensalt());
    }

    /**
     * Rejects the membership matching the provided token details.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws DataAccessException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void reject(int id, String token) throws DataAccessException, IncorrectTokenException, ExpiredTokenException
    {
        Token tokenDB = tokenDAO.get(id);
        if (tokenDB == null)
            throw new IncorrectTokenException();

        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusHours(EXPIRATION_TIME_HOURS));
        if (LocalDateTime.now().isAfter(expiration)) {
            throw new ExpiredTokenException();
        }

        if (!BCrypt.checkpw(token, tokenDB.getHash())) {
            throw new IncorrectTokenException();
        }

        customerDAO.rejectMembership(id);
    }


    /**
     * Verifies the email address issued the provided token.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws DataAccessException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void confirm(int id, String token) throws DataAccessException, IncorrectTokenException, ExpiredTokenException
    {
        Token tokenDB = tokenDAO.get(id);
        if (tokenDB == null)
            throw new IncorrectTokenException();

        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusHours(EXPIRATION_TIME_HOURS));
        if (LocalDateTime.now().isAfter(expiration)) {
            throw new ExpiredTokenException();
        }

        if (!BCrypt.checkpw(token, tokenDB.getHash())) {
            throw new IncorrectTokenException();
        }

        customerDAO.confirmMembership(id);
    }
}
