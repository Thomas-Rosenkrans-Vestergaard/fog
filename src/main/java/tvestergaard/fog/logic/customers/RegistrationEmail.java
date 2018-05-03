package tvestergaard.fog.logic.customers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenSecret;

public class RegistrationEmail implements ApplicationEmail
{

    /**
     * The customer to send the registration confirmation email to.
     */
    private final Customer customer;

    /**
     * The secret component of the token.
     */
    private final TokenSecret secret;

    /**
     * Creates a new {@link RegistrationEmail}.
     *
     * @param customer The customer to send the registration confirmation email to.
     * @param secret   The token secret to send in the email.
     */
    public RegistrationEmail(Customer customer, TokenSecret secret)
    {
        this.customer = customer;
        this.secret = secret;
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
                .subject("Velkommen til Fog Carporte.")
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
        builder.append("<h1>Velkommen til Fog Carporte.</h1>");
        builder.append("<p>For at fortsætte med din kundekonto, skal du først bekræfte din registration.</p>");
        builder.append(String.format("<p>Klik <a href='localhost/fog/account?action=confirm-membership&id=%d&token=%s'>her</a> for at bekræfte din registration.</p>", secret.id, secret.secret));
        builder.append(String.format("<p>Hvis du ikke ønsker at tilmelde dig, kan du istedet klikke <a href='localhost/fog/account?action=reject-membership&id=%d&token=%s'>her</a></p>", secret.id, secret.secret));

        return builder.toString();
    }
}
