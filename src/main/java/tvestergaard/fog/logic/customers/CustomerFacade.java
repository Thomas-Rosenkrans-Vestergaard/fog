package tvestergaard.fog.logic.customers;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;
import static tvestergaard.fog.logic.customers.CustomerValidationException.Reason;
import static tvestergaard.fog.logic.customers.CustomerValidationException.Reason.*;

public class CustomerFacade
{

    /**
     * The {@link CustomerDAO} used to access and make changes to the data storage used by the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * Creates a new {@link CustomerFacade}.
     *
     * @param customerDAO The {@link CustomerDAO} used to access and make changes to the data storage used by the
     *                    application.
     */
    public CustomerFacade(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * Creates a new {@link CustomerFacade} using a {@link MysqlCustomerDAO} with the connection provided from {@link
     * ProductionDataSource#getSource()}.
     */
    public CustomerFacade()
    {
        this.customerDAO = new MysqlCustomerDAO(ProductionDataSource.getSource());
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
     * @throws CustomerValidationException When the provided details are invalid.
     * @throws ApplicationException        When an exception occurs while performing the operation.
     */
    public Customer create(String name,
                           String address,
                           String email,
                           String phone,
                           ContactMethod contactMethod,
                           String password,
                           boolean active) throws CustomerValidationException
    {
        try {
            Set<Reason> reasons = validateCustomerCreation(name, address, email, phone, password);
            if (!reasons.isEmpty())
                throw new CustomerValidationException(reasons);
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
     * Validates the provided parameters for creating a customer.
     *
     * @param name     The name of the customer to create.
     * @param address  The address of the customer to create.
     * @param email    The email address of the customer to create.
     * @param phone    The phone number of the customer to create.
     * @param password The password of the customer to create.
     */
    private Set<Reason> validateCustomer(String name, String address, String email, String phone, String password)
    {
        Set<Reason> reasons = new HashSet<>();

        if (name == null || name.isEmpty())
            reasons.add(NAME_EMPTY);
        else if (name.length() > 255)
            reasons.add(NAME_LONGER_THAN_255);

        if (address == null || address.isEmpty())
            reasons.add(ADDRESS_EMPTY);
        else if (address.length() > 255)
            reasons.add(ADDRESS_LONGER_THAN_255);

        if (email == null || !EmailValidator.getInstance().isValid(email))
            reasons.add(EMAIL_INVALID);
        else if (email.length() > 255)
            reasons.add(EMAIL_LONGER_THAN_255);

        if (phone == null || phone.isEmpty())
            reasons.add(PHONE_EMPTY);
        else if (phone.length() > 30)
            reasons.add(PHONE_LONGER_THAN_30);

        if (password != null && password.length() < 4)
            reasons.add(PASSWORD_SHORTER_THAN_4);

        return reasons;
    }

    /**
     * Updates the entity in the application to match the provided {@code customer}.
     *
     * @param customer The customer to update the entity in the application to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException        When an exception occurs while performing the operation.
     * @throws CustomerValidationException When the provided customer information is considered invalid.
     */
    public boolean update(Customer customer) throws ApplicationException, CustomerValidationException
    {
        try {
            Set<Reason> reasons = validateCustomerUpdate(customer);
            if (!reasons.isEmpty())
                throw new CustomerValidationException(reasons);
            return customerDAO.update(customer);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Validates the customer instance provided to the update method.
     *
     * @param customer The customer instance to validate.
     * @return Any reasons why the provided customer is invalid.
     * @throws DataAccessException
     * @see CustomerFacade#update(Customer)
     */
    private Set<Reason> validateCustomerUpdate(Customer customer) throws DataAccessException
    {
        Set<Reason> reasons = validateCustomer(
                customer.getName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPassword());

        Customer search =
                customerDAO.first(where(eq(EMAIL, customer.getEmail()), and(not(CustomerColumn.ID, customer.getId()))));
        if (search != null)
            reasons.add(EMAIL_TAKEN);

        return reasons;
    }


    private Set<Reason> validateCustomerCreation(String name, String address, String email, String phone,
                                                 String password)
            throws DataAccessException
    {
        Set<Reason> reasons = validateCustomer(name, address, email, phone, password);
        Customer search = customerDAO.first(where(eq(EMAIL, email)));
        if (search != null)
            reasons.add(EMAIL_TAKEN);

        return reasons;
    }
}
