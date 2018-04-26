package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class CustomerFacade
{

    /**
     * The {@link CustomerDAO} used to access and make changes to the data storage used by the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * The validator used the validate the information provided to the {@link CustomerFacade}.
     *
     * @see CustomerFacade#create(String, String, String, String, ContactMethod, String, boolean)
     * @see CustomerFacade#update(Customer)
     */
    private final CustomerValidator validator;

    /**
     * Creates a new {@link CustomerFacade}.
     *
     * @param customerDAO The {@link CustomerDAO} used to access and make changes to the data storage used by the
     *                    application.
     */
    public CustomerFacade(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
        this.validator = new CustomerValidator(customerDAO);
    }

    /**
     * Creates a new {@link CustomerFacade} using a {@link MysqlCustomerDAO} with the connection provided from {@link
     * ProductionDataSource#getSource()}.
     */
    public CustomerFacade()
    {
        this(new MysqlCustomerDAO(ProductionDataSource.getSource()));
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
     * @param name          The name of the customer to create.
     * @param address       The address of the customer to create.
     * @param email         The email address of the customer to create.
     * @param phone         The phone number of the customer to create.
     * @param password      The password of the customer to create.
     * @param contactMethod The preferred contact method of the customer to create.
     * @param active        Whether or not the customer can be applied to orders.
     * @return The customer instance representing the newly created customer.
     * @throws CustomerValidatorException When the provided details are invalid.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     */
    public Customer create(String name,
                           String address,
                           String email,
                           String phone,
                           ContactMethod contactMethod,
                           String password,
                           boolean active) throws CustomerValidatorException
    {
        try {
            Set<CustomerError> reasons = validator.validateCreate(name, address, email, phone, password);
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            return customerDAO.create(name, address, email, phone, hash(password), contactMethod, active);
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
    private String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Updates the entity in the application to match the provided {@code customer}.
     *
     * @param customer The customer to update the entity in the application to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws CustomerValidatorException When the provided customer information is considered invalid.
     */
    public boolean update(Customer customer) throws ApplicationException, CustomerValidatorException
    {
        try {
            Set<CustomerError> reasons = validator.validateUpdate(customer);
            if (!reasons.isEmpty())
                throw new CustomerValidatorException(reasons);
            return customerDAO.update(customer);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
