package tvestergaard.fog.logic.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.*;
import tvestergaard.fog.data.tokens.MysqlTokenDAO;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;

public class CustomerFacade
{

    /**
     * The {@link CustomerDAO} used to access and make changes to the data storage used by the application.
     */
    private final CustomerDAO     customerDAO;
    private final EmailChallenger emailChallenger;

    /**
     * The validator used the validate the information provided to the {@link CustomerFacade}.
     */
    private final CustomerValidator validator;
    private final PasswordResetter  passwordResetter;

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
        this.emailChallenger = new EmailChallenger(customerDAO, tokenDAO, new SimpleJavaMailer());
        this.passwordResetter = new PasswordResetter(customerDAO, tokenDAO, new SimpleJavaMailer(), new TokenGenerator());
    }

    /**
     * Creates a new {@link CustomerFacade} using a {@link MysqlCustomerDAO} with the connection provided from {@link
     * ProductionDataSource#getSource()}.
     */
    public CustomerFacade()
    {
        MysqlDataSource source = ProductionDataSource.getSource();
        this.customerDAO = new MysqlCustomerDAO(source);
        TokenDAO tokenDAO = new MysqlTokenDAO(source);
        this.validator = new CustomerValidator(customerDAO);
        this.emailChallenger = new EmailChallenger(customerDAO, tokenDAO, new SimpleJavaMailer());
        this.passwordResetter = new PasswordResetter(customerDAO, tokenDAO, new SimpleJavaMailer(), new TokenGenerator());
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
    public Customer register(String name,
                             String address,
                             String email,
                             String phone,
                             String password,
                             boolean active) throws CustomerValidatorException
    {
        try {
            Set<CustomerError> reasons = validator.validateRegister(name, address, email, phone, password);
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            CustomerBlueprint blueprint = Customer.blueprint(name, address, email, phone, password, active);
            blueprint.setPassword(hash(password));
            Customer customer = customerDAO.create(blueprint);
            emailChallenger.challenge(customer);
            return customer;
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
     * @throws NoPasswordException When the provided customer has no password.
     */
    public Customer authenticate(String email, String password) throws NoPasswordException
    {
        try {
            Customer customer = customerDAO.first(where(eq(EMAIL, email)));
            if (customer == null)
                return null;

            if (customer.getPassword() == null)
                throw new NoPasswordException();

            return BCrypt.checkpw(password, customer.getPassword()) ? customer : null;
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Rejects the membership matching the provided token details.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws ApplicationException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void reject(int id, String token) throws IncorrectTokenException, ExpiredTokenException
    {
        try {
            emailChallenger.reject(id, token);
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
     * Hashes the provided password using the b-crypt algorithm.
     *
     * @param password The password to hash.
     * @return The resulting digest.
     */
    public static String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
            CustomerUpdater    updater = Customer.updater(id, name, address, email, phone, password, active);
            Set<CustomerError> reasons = validator.validateUpdate(id, name, address, email, phone, password);
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            return customerDAO.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Sends an email to the provided email address containing a link where the customer can reset their password.
     *
     * @param email The email address to send the password reset link to.
     * @throws ApplicationException  When a generic exception occurs.
     * @throws UnknownEmailException When the provided email is not registered with the application.
     */
    public void sendPasswordReset(String email) throws UnknownEmailException
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
            passwordResetter.reset(tokenId, tokenSecret, newPassword);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
