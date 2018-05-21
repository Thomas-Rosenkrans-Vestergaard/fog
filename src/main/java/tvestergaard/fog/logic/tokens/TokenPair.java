package tvestergaard.fog.logic.tokens;

/**
 * Represents a token with an id and a secret key.
 */
public class TokenPair
{

    /**
     * The unique identifier of the token.
     */
    public final int id;

    /**
     * The secret key needed to authenticate the token.
     */
    public final String secret;

    /**
     * Creates a new {@link TokenPair}.
     *
     * @param id     The unique identifier of the token.
     * @param secret The secret password needed to authenticate the token.
     */
    public TokenPair(int id, String secret)
    {
        this.id = id;
        this.secret = secret;
    }

    /**
     * Returns the unique identifier of the token.
     *
     * @return The unique identifier of the token.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the secret key needed to authenticate the token.
     *
     * @return The secret key needed to authenticate the token.
     */
    public String getSecret()
    {
        return this.secret;
    }
}
