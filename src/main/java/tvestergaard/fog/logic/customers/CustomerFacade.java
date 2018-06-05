package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.customers.*;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.tokens.*;

import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.ID;

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
     * @param customerDAO The {@link CustomerDAO} used to access and make changes to the customers in the application.
     * @param tokenDAO    The {@link TokenDAO} used to access and make changes to the tokens in the application.
     * @param mailer      The object responsible for sending emails to customers.
     */
    public CustomerFacade(CustomerDAO customerDAO, TokenDAO tokenDAO, ApplicationMailer mailer)
    {
        this.customerDAO = customerDAO;
        this.validator = new CustomerValidator(customerDAO);

        TokenGenerator     tokenGenerator     = new TokenGenerator();
        TokenIssuer        tokenIssuer        = new TokenIssuer(tokenDAO, tokenGenerator);
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(tokenDAO, 24 * 60 * 60);

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
    public List<Customer> get(Constraints<CustomerColumn> constraints)
    {
        try {
            return customerDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the customers in the application.
     *
     * @return The resulting customers.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Customer> get()
    {
        try {
            return customerDAO.get();
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
    public Customer first(Constraints<CustomerColumn> constraints)
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
     * @param tokenId     The id of the token.
     * @param tokenSecret The secret key of the token.
     * @throws ApplicationException    When an exception occurs while performing the operation.
     * @throws IncorrectTokenException When the provided token could not be validated.
     * @throws ExpiredTokenException   When the token secret was valid, but the token had expired.
     */
    public void confirm(int tokenId, String tokenSecret) throws IncorrectTokenException, ExpiredTokenException
    {
        try {
            emailChallenger.confirm(new TokenPair(tokenId, tokenSecret));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the application to match the provided {@code customer}.
     *
     * @param id      The id of the customer to update.
     * @param name    The new name.
     * @param address The new address.
     * @param email   The new email.
     * @param phone   The new phone.
     * @return {@code true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws UnknownCustomerException   When a customer with the provided id does not exist.
     * @throws CustomerValidatorException When the provided customer information is considered invalid.
     */
    public boolean update(int id,
                          String name,
                          String address,
                          String email,
                          String phone) throws UnknownCustomerException, CustomerValidatorException
    {
        try {

            Customer customer = customerDAO.first(where(eq(ID, id)));

            if (customer == null)
                throw new UnknownCustomerException();

            boolean            verified = email.equals(customer.getEmail());
            CustomerUpdater    updater  = CustomerUpdater.from(id, name, address, email, phone, customer.getPassword(), customer.isActive(), verified);
            Set<CustomerError> reasons  = validator.validateUpdate(id, name, address, email, phone, customer.getPassword());
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            boolean result = customerDAO.update(updater);
            if (result == true && !email.equals(customer.getEmail())) {
                emailChallenger.challenge(customerDAO.first(where(eq(ID, customer.getId()))));
            }

            return result;

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Marks the customer active.
     *
     * @param customerId The id of the customer to mark active.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException     When a data storage exception occurs while performing the operation.
     * @throws UnknownCustomerException When a customer with the provided id does not exist.
     */
    public boolean activate(int customerId) throws ApplicationException, UnknownCustomerException
    {
        try {
            return customerDAO.activate(customerId);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Marks the customer inactive.
     *
     * @param customerId The id of the customer to mark inactive.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException     When a data storage exception occurs while performing the operation.
     * @throws UnknownCustomerException When a customer with the provided id does not exist.
     */
    public boolean inactivate(int customerId) throws ApplicationException, UnknownCustomerException
    {
        try {
            return customerDAO.inactivate(customerId);
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
     * @return The customer instance representing the newly created customer.
     * @throws CustomerValidatorException When the provided details are invalid.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     */
    public Customer register(String name, String address, String email, String phone, String password)
            throws CustomerValidatorException
    {
        try {
            return authentication.register(name, address, email, phone, password);
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
     * @throws CustomerAuthentication    When the provided email or password is incorrect.
     */
    public Customer authenticate(String email, String password) throws InactiveCustomerException, CustomerAuthenticationException
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
            passwordResetter.reset(new TokenPair(tokenId, tokenSecret), newPassword);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the password of the customer with the provided id.
     *
     * @param customerId  The id of the customer to update the password of.
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @throws ApplicationException            When a data storage exception occurs.
     * @throws UnknownCustomerException        When the customer is unknown to the application.
     * @throws CustomerAuthenticationException When the provided old password does not match the current password of the customer.
     */
    public void updatePassword(int customerId, String oldPassword, String newPassword) throws ApplicationException,
                                                                                              UnknownCustomerException,
                                                                                              CustomerAuthenticationException
    {
        try {
            Customer customer = customerDAO.first(where(eq(ID, customerId)));
            if (customer == null)
                throw new UnknownCustomerException();
            if (!check(customer.getPassword(), oldPassword))
                throw new CustomerAuthenticationException();

            customer.setPassword(hash(newPassword));
            customerDAO.update(customer);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Re-sends a email confirmation email to the user with the provided id.
     *
     * @param customerId The id of the customer to send the confirmation email to.
     * @throws ApplicationException     When a data storage exception occurs during the operation.
     * @throws UnknownCustomerException When a customer with the provided id does not exist.
     */
    public void resendConfirmation(int customerId) throws ApplicationException, UnknownCustomerException
    {
        try {
            Customer customer = customerDAO.first(where(eq(ID, customerId)));
            if (customer == null)
                throw new UnknownCustomerException();

            emailChallenger.challenge(customer);

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the hash of the provided password using bcrypt.
     *
     * @param password The password to hash.
     * @return The hash of the provided password using bcrypt.
     */
    private String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks if the provided password matches the provided hash.
     *
     * @param hash     The hash.
     * @param password The password to check against the password.
     * @return {@code true} if the provided password matches the provided hash.
     */
    private boolean check(String hash, String password)
    {
        return BCrypt.checkpw(password, hash);
    }
}
