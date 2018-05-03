package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.tokens.TokenUse;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.tokens.*;

import java.security.SecureRandom;

/**
 * Challenges new customers to verify their email address.
 */
public class EmailVerifier
{

    /**
     * The source of randomness used to generate the tokens.
     */
    private final SecureRandom random = new SecureRandom();

    /**
     * The customer DAO used to
     */
    private final CustomerDAO customerDAO;

    private final TokenIssuer        tokenIssuer;
    private final TokenAuthenticator tokenAuthenticator;
    private final ApplicationMailer  mailer;

    /**
     * The number of hours the token can be used from when it was first issued.
     */
    private final int EXPIRATION_TIME_HOURS = 24;

    public EmailVerifier(CustomerDAO customerDAO, TokenIssuer tokenIssuer, TokenAuthenticator tokenAuthenticator, ApplicationMailer mailer)
    {
        this.customerDAO = customerDAO;
        this.tokenIssuer = tokenIssuer;
        this.tokenAuthenticator = tokenAuthenticator;
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
        TokenSecret       secret = tokenIssuer.issue(customer, TokenUse.EMAIL_VERIFICATION);
        RegistrationEmail email  = new RegistrationEmail(customer, secret);
        mailer.send(email);
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
        if (!tokenAuthenticator.authenticate(new TokenSecret(id, token), TokenUse.EMAIL_VERIFICATION)) {
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
        if (!tokenAuthenticator.authenticate(new TokenSecret(id, token), TokenUse.EMAIL_VERIFICATION)) {
            throw new IncorrectTokenException();
        }

        customerDAO.confirmMembership(id);
    }
}
