package tvestergaard.fog.data.tokens;

import tvestergaard.fog.data.DataAccessException;

public interface TokenDAO
{

    /**
     * Inserts a new registration token into the data storage.
     *
     * @param customer The customer the token is issued to.
     * @param token    The token to insert.
     * @param use      The usage of the token to create.
     * @return An instance representing the newly inserted token.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Token create(int customer, String token, TokenUse use) throws DataAccessException;

    /**
     * Returns the registration token with the provided id.
     *
     * @param id  The id of the registration token to return.
     * @param use The use of the registration token to return.
     * @return The registration token with the provided id. Returns {@code null} in case no registration token with the
     * provided id exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Token get(int id, TokenUse use) throws DataAccessException;
}
