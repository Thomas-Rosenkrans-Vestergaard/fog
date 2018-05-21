package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.tokens.TokenUse;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.tokens.*;

/**
 * Challenges new customers to verify their email address.
 */
public class EmailVerifier
{

    /**
     * The customer DAO used to access the customers known to the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * The object responsible for issuing the token sent to the email address of the customer.
     */
    private final TokenIssuer tokenIssuer;

    /**
     * The object responsible for authenticating the token sent to the email address of the customer.
     */
    private final TokenAuthenticator tokenAuthenticator;

    /**
     * The object responsible for sending the verification email to the email address of the customer.
     */
    private final ApplicationMailer mailer;

    /**
     * Creates a new {@link EmailVerifier}.
     *
     * @param customerDAO        The customer DAO used to access the customers known to the application.
     * @param tokenIssuer        The object responsible for issuing the token sent to the email address of the customer.
     * @param tokenAuthenticator The object responsible for authenticating the token sent to the email address of the customer.
     * @param mailer             The object responsible for sending the verification email to the email address of the customer.
     */
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
     * @throws DataAccessException When a data storage exception occurs.
     */
    public void challenge(Customer customer) throws DataAccessException
    {
        TokenPair                 secret = tokenIssuer.issue(customer, TokenUse.EMAIL_VERIFICATION);
        CustomerVerificationEmail email  = new CustomerVerificationEmail(customer, secret);
        mailer.send(email);
    }

    /**
     * Verifies the email address issued the provided token.
     *
     * @param tokenPair The token to use when confirming the email address.
     * @throws DataAccessException     When an exception occurs while performing the operation.
     * @throws IncorrectTokenException When the provided token could not be validated.
     * @throws ExpiredTokenException   When the token secret was valid, but the token had expired.
     */
    public void confirm(TokenPair tokenPair) throws DataAccessException, IncorrectTokenException, ExpiredTokenException
    {
        if (!tokenAuthenticator.authenticate(tokenPair, TokenUse.EMAIL_VERIFICATION)) {
            throw new IncorrectTokenException();
        }

        customerDAO.confirmMembership(tokenPair.id);
    }
}
