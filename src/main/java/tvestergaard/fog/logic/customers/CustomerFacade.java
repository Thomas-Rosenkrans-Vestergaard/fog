package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.CustomerUpdater;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.email.SimpleJavaMailer;
import tvestergaard.fog.logic.tokens.*;

import java.util.List;
import java.util.Set;

public class CustomerFacade
{

    /**
     * The {@link CustomerDAO} used to access and make changes to the data storage used by the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * The object responsible for verifying email addresses.
     */
    private final EmailVerifier emailChallenger;

    /**
     * The validator used the validate the information provided to the {@link CustomerFacade}.
     */
    private final CustomerValidator validator;

    /**
     * The object responsible for resetting passwords in the application.
     */
    private final PasswordResetter passwordResetter;

    /**
     * The object responsible for authentication customers.
     */
    private final CustomerAuthentication authentication;

    /**
     * Creates a new {@link CustomerFacade}.
     *
     * @param customerDAO The {@link CustomerDAO} used to access and make changes to the data storage used by the
     *                    application.
     */
    public CustomerFacade(CustomerDAO customerDAO, TokenDAO tokenDAO)
    {
        this.customerDAO = customerDAO;
        this.validator = new CustomerValidator(customerDAO);

        ApplicationMailer  mailer             = new SimpleJavaMailer();
        TokenGenerator     tokenGenerator     = new TokenGenerator();
        TokenIssuer        tokenIssuer        = new TokenIssuer(tokenDAO, tokenGenerator);
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(tokenDAO, 24);

        this.emailChallenger = new EmailVerifier(customerDAO, tokenIssuer, tokenAuthenticator, mailer);
        this.passwordResetter = new PasswordResetter(customerDAO, tokenIssuer, tokenAuthenticator, mailer);
        this.authentication = new CustomerAuthentication(customerDAO, validator, emailChallenger);
    }

    /**
     * Returns the customers in the application. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting customers.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Customer> get(Constraint<CustomerColumn>... constraints)
    {
        try {
            return customerDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first customer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first customer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Customer first(Constraint<CustomerColumn>... constraints)
    {
        try {
            return customerDAO.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Confirms the membership matching the provided token details.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws ApplicationException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void confirm(int id, String token) throws IncorrectTokenException, ExpiredTokenException
    {
        try {
            emailChallenger.confirm(id, token);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the application to match the provided {@code customer}.
     *
     * @param id       The id of the customer to update.
     * @param name     The new name.
     * @param address  The new address.
     * @param email    The new email.
     * @param phone    The new phone.
     * @param password The new password.
     * @param active   The new active status.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws CustomerValidatorException When the provided customer information is considered invalid.
     */
    public boolean update(int id,
                          String name,
                          String address,
                          String email,
                          String phone,
                          String password,
                          boolean active) throws CustomerValidatorException
    {
        try {
            CustomerUpdater    updater = CustomerUpdater.from(id, name, address, email, phone, password, active);
            Set<CustomerError> reasons = validator.validateUpdate(id, name, address, email, phone, password);
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            return customerDAO.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new customer into the application.
     *
     * @param name     The name of the customer to create.
     * @param address  The address of the customer to create.
     * @param email    The email address of the customer to create.
     * @param phone    The phone number of the customer to create.
     * @param password The password of the customer to create.
     * @param active   Whether or not the customer can be applied to orders.
     * @return The customer instance representing the newly created customer.
     * @throws CustomerValidatorException When the provided details are invalid.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     */
    public Customer register(String name, String address, String email, String phone, String password, boolean active)
            throws CustomerValidatorException
    {
        try {
            return authentication.register(name, address, email, phone, password, active);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Attempts to authenticate a customer using the provided email and password.
     *
     * @param email    The email to authenticate with.
     * @param password The password to authenticate with.
     * @return The customer who was authenticated. {@code null} in case no customer with the provided credentials exist.
     * @throws InactiveCustomerException When the provided customer is marked inactive.
     */
    public Customer authenticate(String email, String password) throws InactiveCustomerException
    {
        try {
            return authentication.authenticate(email, password);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Sends an email to the provided email address containing a link where the customer can reset their password.
     *
     * @param email The email address to send the password reset link to.
     * @throws ApplicationException      When a generic exception occurs.
     * @throws UnknownEmailException     When the provided email is not registered with the application.
     * @throws InactiveCustomerException When the customer with the provided email is inactive, and can therefor not
     *                                   reset their password.
     */
    public void sendPasswordReset(String email) throws UnknownEmailException, InactiveCustomerException
    {
        try {
            passwordResetter.send(email);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Attempts to reset the password of some customer using the provided token and the new password.
     *
     * @param tokenId     The id of the token.
     * @param tokenSecret The secret token.
     * @param newPassword The new password.
     * @throws ApplicationException    When a generic exception occurs.
     * @throws IncorrectTokenException When the provided token credentials are incorrect.
     * @throws ExpiredTokenException   When the provided token exists, but have expired.
     */
    public void resetPassword(int tokenId, String tokenSecret, String newPassword) throws IncorrectTokenException, ExpiredTokenException
    {
        try {
            passwordResetter.reset(new TokenSecret(tokenId, tokenSecret), newPassword);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the number of customers in the data storage.
     *
     * @return The number of customers in the data storage.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public int size() throws ApplicationException
    {
        try {
            return customerDAO.size();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
