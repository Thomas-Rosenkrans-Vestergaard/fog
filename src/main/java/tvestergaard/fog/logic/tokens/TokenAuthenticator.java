package tvestergaard.fog.logic.tokens;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.tokens.Token;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.data.tokens.TokenUse;

import java.time.LocalDateTime;

/**
 * Authenticates provided tokens using the provided token dao to access the tokens persisted in the application.
 */
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
     * @param tokenDAO          The TokenDAO used to query the issued tokens from data storage.
     * @param expirationSeconds The number of seconds the token should be considered valid.
     */
    public TokenAuthenticator(TokenDAO tokenDAO, int expirationSeconds)
    {
        this.tokenDAO = tokenDAO;
        this.expirationHours = expirationSeconds;
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
    public boolean authenticate(TokenPair secret, TokenUse use) throws ExpiredTokenException, DataAccessException
    {
        Token tokenDB = tokenDAO.get(secret.id, use);
        if (tokenDB == null)
            return false;

        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusSeconds(expirationHours));
        if (LocalDateTime.now().isAfter(expiration)) {
            throw new ExpiredTokenException();
        }

        return BCrypt.checkpw(secret.secret, tokenDB.getHash());
    }
}
