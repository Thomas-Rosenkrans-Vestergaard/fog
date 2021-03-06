package tvestergaard.fog.logic.tokens;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Generates the secret key for a token.
 */
public class TokenGenerator
{

    /**
     * The source of randomness used to generate tokens.
     */
    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a secure token.
     *
     * @return The resulting secure token.
     */
    public String generate()
    {
        byte bytes[] = new byte[128];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
