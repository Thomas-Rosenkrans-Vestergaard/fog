package tvestergaard.fog.logic.tokens;

public class TokenPair
{

    /**
     * The unique identifier of the token.
     */
    public final int id;

    /**
     * The secret password needed to authenticate the token.
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
}
