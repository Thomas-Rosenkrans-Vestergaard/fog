package tvestergaard.fog.data.tokens;

import tvestergaard.fog.data.DataAccessException;

public interface TokenDAO
{


    /**
     * Inserts a new registration token into the data storage.
     *
     * @param customer The customer the token is issued to.
     * @param token    The token to insert.
     * @return An instance representing the newly inserted token.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Token create(int customer, String token) throws DataAccessException;

    /**
     * Returns the registration token with the provided id.
     *
     * @param id The id of the registration token to return.
     * @return The registration token with the provided id. Returns {@code null} in case no registration token with the
     * provided id exists.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    Token get(int id) throws DataAccessException;

    /**
     * Confirms the membership confirmation challenge of the provided token.
     *
     * @param token The id of the token to confirm.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    void confirm(int token) throws DataAccessException;

    /**
     * Rejects the membership confirmation challenge of the provided token.
     *
     * @param token The id of the token to reject.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    void reject(int token) throws DataAccessException;
}
