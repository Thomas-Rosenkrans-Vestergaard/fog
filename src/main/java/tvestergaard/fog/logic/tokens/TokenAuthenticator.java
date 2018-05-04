package tvestergaard.fog.logic.tokens;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.tokens.Token;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.TokenUse;

import java.time.LocalDateTime;

public class TokenAuthenticator
{

    /**
     * The TokenDAO used to query the issued tokens from data storage.
     */
    private final TokenDAO tokenDAO;

    /**
     * The number of hours the token should be considered valid.
     */
    private final int expirationHours;

    /**
     * Creates a new {@link TokenAuthenticator}.
     *
     * @param tokenDAO        The TokenDAO used to query the issued tokens from data storage.
     * @param expirationHours The number of hours the token should be considered valid.
     */
    public TokenAuthenticator(TokenDAO tokenDAO, int expirationHours)
    {
        this.tokenDAO = tokenDAO;
        this.expirationHours = expirationHours;
    }

    /**
     * Attempts to validate the provided token.
     *
     * @param secret The token secret to authenticate.
     * @param use    The usage of the token to authenticate.
     * @return {@code true} when the token is authenticated.
     * @throws ExpiredTokenException When the provided token was valid, but has since expired.
     * @throws DataAccessException   When a data storage exception occurs while performing the operation.
     */
    public boolean authenticate(TokenSecret secret, TokenUse use) throws ExpiredTokenException, DataAccessException
    {
        Token tokenDB = tokenDAO.get(secret.id, use);
        if (tokenDB == null)
            return false;

        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusHours(expirationHours));
        if (LocalDateTime.now().isAfter(expiration)) {
            throw new ExpiredTokenException();
        }

        return BCrypt.checkpw(secret.secret, tokenDB.getHash());
    }
}
