package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;

import java.util.List;

public interface CustomerDAO
{

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
     * Updates the entity in the data storage to match the provided {@code customer}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(CustomerUpdater updater) throws DataAccessException;

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
