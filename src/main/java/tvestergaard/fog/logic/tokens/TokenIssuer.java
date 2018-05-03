package tvestergaard.fog.logic.tokens;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.tokens.Token;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.TokenUse;

/**
 * Issues a single use token for some customer for some provided use.
 */
public class TokenIssuer
{

    /**
     * The token dao to use when persisting the issued token.
     */
    private final TokenDAO tokenDAO;

    /**
     * The object that generates the tokens issued.
     */
    private final TokenGenerator tokenGenerator;

    /**
     * Creates a new {@link TokenIssuer}.
     *
     * @param tokenDAO       The token dao to use when persisting the issued token.
     * @param tokenGenerator The object that generates the tokens issued.
     */
    public TokenIssuer(TokenDAO tokenDAO, TokenGenerator tokenGenerator)
    {
        this.tokenDAO = tokenDAO;
        this.tokenGenerator = tokenGenerator;
    }

    /**
     * Generates a new token for the provided use.
     *
     * @param customer The customer the token is issued to.
     * @param use      The purpose of the token.
     * @return TokenSecret representing the newly created token.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    public TokenSecret issue(Customer customer, TokenUse use) throws DataAccessException
    {
        String secret = tokenGenerator.generate();
        Token  token  = tokenDAO.create(customer.getId(), hash(secret), use);
        return new TokenSecret(token.getId(), secret);
    }

    /**
     * Hashes the provided token using the b-crypt algorithm.
     *
     * @param token The token to hash.
     * @return The resulting hash or digest.
     */
    private String hash(String token)
    {
        return BCrypt.hashpw(token, BCrypt.gensalt());
    }
}
