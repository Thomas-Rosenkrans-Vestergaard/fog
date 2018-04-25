package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface CustomerDAO
{

    /**
     * Returns the customers in the data storage.
     * The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting customers.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Customer> get(Constraint<CustomerColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first customer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first customer matching the provided constraints. Returns {@code null} when no
     * constraints matches the provided constraints.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Customer first(Constraint<CustomerColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new customer into the data storage.
     *
     * @param name          The name of the customer to create.
     * @param address       The address of the customer to create.
     * @param phone         The phone number of the customer to create.
     * @param password      The password of the customer to create.
     * @param contactMethod The preferred contact method of the customer to create.
     * @param active        Whether or not the customer can be applied to orders.
     * @return The customer instance representing the newly created customer.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Customer create(String name, String address, String email, String phone, String password, Customer.ContactMethod contactMethod, boolean active) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code customer}.
     *
     * @param customer The customer to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    boolean update(Customer customer) throws DataAccessException;
}
