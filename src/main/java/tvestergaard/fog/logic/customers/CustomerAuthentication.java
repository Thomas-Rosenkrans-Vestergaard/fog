package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.CustomerDAO;

import java.util.Set;

public class CustomerAuthentication
{

    /**
     * The customer dao used to access the customers in the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * The object responsible the validating customer information.
     */
    private final CustomerValidator validator;

    /**
     * The object responsible for verifying customer emails.
     */
    private final EmailVerifier emailChallenger;

    /**
     * Creates a new {@link CustomerAuthentication}:
     *
     * @param customerDAO     The customer dao used to access the customers in the application.
     * @param validator       The object responsible the validating customer information.
     * @param emailChallenger The object responsible for verifying customer emails.
     */
    public CustomerAuthentication(CustomerDAO customerDAO, CustomerValidator validator, EmailVerifier emailChallenger)
    {
        this.customerDAO = customerDAO;
        this.validator = validator;
        this.emailChallenger = emailChallenger;
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
     * @throws DataAccessException        When a data storage exception occurs.
     * @throws CustomerValidatorException When the provided details are invalid.
     */
    public Customer register(String name, String address, String email, String phone, String password)
            throws DataAccessException, CustomerValidatorException
    {
        Set<CustomerError> reasons = validator.validateRegister(name, address, email, phone, password);
        if (!reasons.isEmpty())
            throw new CustomerValidatorException(reasons);
        CustomerBlueprint blueprint = CustomerBlueprint.from(name, address, email, phone, password, true);
        blueprint.setPassword(hash(password));
        Customer customer = customerDAO.create(blueprint);
        emailChallenger.challenge(customer);
        return customer;
    }

    /**
     * Attempts to authenticate a customer using the provided email and password.
     *
     * @param email    The email to authenticate with.
     * @param password The password to authenticate with.
     * @return The customer who was authenticated.
     * @throws DataAccessException             When a data storage exception occurs.
     * @throws InactiveCustomerException       When the provided customer is marked inactive.
     * @throws CustomerAuthenticationException When the provided email or password is incorrect.
     */
    public Customer authenticate(String email, String password) throws DataAccessException,
                                                                       InactiveCustomerException,
                                                                       CustomerAuthenticationException
    {
        Customer customer = customerDAO.get(email);
        if (customer == null)
            throw new CustomerAuthenticationException();

        if (!customer.isActive())
            throw new InactiveCustomerException();

        if (!BCrypt.checkpw(password, customer.getPassword()))
            throw new CustomerAuthenticationException();

        return customer;
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
}
