package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.CustomerDAO;

import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;

public class CustomerAuthentication
{

    private final CustomerDAO       customerDAO;
    private final CustomerValidator validator;
    private final EmailChallenger   emailChallenger;

    public CustomerAuthentication(CustomerDAO customerDAO, CustomerValidator validator, EmailChallenger emailChallenger)
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
     * @param active   Whether or not the customer can be applied to orders.
     * @return The customer instance representing the newly created customer.
     * @throws DataAccessException        When a data storage exception occurs.
     * @throws CustomerValidatorException When the provided details are invalid.
     */
    public Customer register(String name,
                             String address,
                             String email,
                             String phone,
                             String password,
                             boolean active) throws DataAccessException, CustomerValidatorException
    {
        Set<CustomerError> reasons = validator.validateRegister(name, address, email, phone, password);
        if (!reasons.isEmpty())
            throw new CustomerValidatorException(reasons);
        CustomerBlueprint blueprint = CustomerBlueprint.from(name, address, email, phone, password, active);
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
     * @return The customer who was authenticated. {@code null} in case no customer with the provided credentials exist.
     * @throws DataAccessException       When a data storage exception occurs.
     * @throws InactiveCustomerException When the provided customer is marked inactive.
     */
    public Customer authenticate(String email, String password) throws DataAccessException, InactiveCustomerException
    {
        Customer customer = customerDAO.first(where(eq(EMAIL, email)));
        if (customer == null)
            return null;

        if (!customer.isActive())
            throw new InactiveCustomerException();

        return BCrypt.checkpw(password, customer.getPassword()) ? customer : null;
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
