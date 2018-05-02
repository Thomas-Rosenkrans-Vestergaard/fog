package tvestergaard.fog.logic.customers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.email.ApplicationEmail;

public class PasswordResetEmail implements ApplicationEmail
{

    private final Customer customer;
    private final int      tokenId;
    private final String   tokenSecret;

    public PasswordResetEmail(Customer customer, int tokenId, String tokenSecret)
    {
        this.customer = customer;
        this.tokenId = tokenId;
        this.tokenSecret = tokenSecret;
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
        builder.append(String.format("/fog/reset-password?tokenId=%d&tokenSecret=%s", tokenId, tokenSecret));

        return builder.toString();
    }
}
