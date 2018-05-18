package tvestergaard.fog.logic.customers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenPair;

public class PasswordResetEmail implements ApplicationEmail
{

    /**
     * The customer to send the password-reset email to.
     */
    private final Customer customer;

    /**
     * The token to send within the password-reset email.
     */
    private final TokenPair token;

    /**
     * Creates a new {@link PasswordResetEmail}.
     *
     * @param customer The customer to send the password-reset email to.
     * @param token    The token to send within the password-reset email.
     */
    public PasswordResetEmail(Customer customer, TokenPair token)
    {
        this.customer = customer;
        this.token = token;
    }

    /**
     * Builds the Email instance that can be sent using the SimpleJavaMail library.
     *
     * @return The email instance.
     */
    @Override public Email build()
    {
        return new EmailBuilder()
                .to(customer.getName(), customer.getEmail())
                .from("Fog carporte", "fog.carporte@gmail.com")
                .subject("Glemt adgangskode.")
                .textHTML(generateHTML())
                .build();
    }

    /**
     * Generates the html for the registration confirmation email.
     *
     * @return The html for the registration confirmation email.
     */
    private String generateHTML()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Glemt adgangskode.</h1>");
        builder.append(String.format("/fog/reset-password?tokenId=%d&tokenSecret=%s", token.id, token.secret));

        return builder.toString();
    }
}
