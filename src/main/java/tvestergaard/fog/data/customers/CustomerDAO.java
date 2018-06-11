package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;
import static tvestergaard.fog.data.customers.CustomerColumn.ID;

public interface CustomerDAO
{

    /**
     * Returns the customers in the data storage.
     *
     * @return The complete list of customers in data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default List<Customer> get() throws DataAccessException
    {
        return get(new Constraints<>());
    }

    /**
     * Returns the customer with the provided id.
     *
     * @param id The id of the customer to return.
     * @return The customer with the provided id. Returns {@code null} if no such customer exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Customer get(int id) throws DataAccessException
    {
        return first(where(eq(ID, id)));
    }

    /**
     * Returns the customer with the provided email.
     *
     * @param email The email address of the customer to return.
     * @return The customer with the provided email address. Returns {@code null} if no such customer exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Customer get(String email) throws DataAccessException
    {
        return first(where(eq(EMAIL, email)));
    }

    /**
     * Returns the customers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting customers.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Customer> get(Constraints<CustomerColumn> constraints) throws DataAccessException;

    /**
     * Returns the first customer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first customer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Customer first(Constraints<CustomerColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new customer into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The customer instance representing the newly created customer.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Customer create(CustomerBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code customer}. Note that the password of the
     * customer is not updated. Use the {@link CustomerDAO#updatePassword(int, String)} method instead.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     * @see CustomerDAO#updatePassword(int, String)
     */
    boolean update(CustomerUpdater updater) throws DataAccessException;

    /**
     * Updates the password of the customer with the provided id to the provided password hash.
     *
     * @param customer     The customer the update the password of.
     * @param passwordHash The hash of the new password.
     * @return {@code true} if the password was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean updatePassword(int customer, String passwordHash) throws DataAccessException;

    /**
     * Marks the customer active.
     *
     * @param customerId The id of the customer to mark active.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean activate(int customerId) throws DataAccessException;

    /**
     * Marks the customer inactive.
     *
     * @param customerId The id of the customer to mark inactive.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean deactivate(int customerId) throws DataAccessException;

    /**
     * Resets the password of the customer the provided token was issued to. The token is then deleted.
     *
     * @param tokenId     The token identifying the customer to reset the password of.
     * @param newPassword The new password.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    void resetPassword(int tokenId, String newPassword) throws DataAccessException;

    /**
     * Confirms the membership confirmation challenge of the provided token.
     *
     * @param token The id of the token to confirm.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    void confirmMembership(int token) throws DataAccessException;
}
