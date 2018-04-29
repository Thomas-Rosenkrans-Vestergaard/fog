package tvestergaard.fog.logic.customers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.email.ApplicationEmail;

public class RegistrationEmail implements ApplicationEmail
{

    /**
     * The customer to send the registration confirmation email to.
     */
    private final Customer customer;

    /**
     * The registration token id.
     */
    private final int tokenId;

    /**
     * The registration token.
     */
    private final String token;

    /**
     * Creates a new {@link RegistrationEmail}.
     *
     * @param customer The customer to send the registration confirmation email to.
     * @param tokenId  The registration token id.
     * @param token    The registration token.
     */
    public RegistrationEmail(Customer customer, int tokenId, String token)
    {
        this.customer = customer;
        this.tokenId = tokenId;
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
        builder.append("<!DOCTYPE html>");
        builder.append("<html>");
        builder.append("<body>");
        builder.append("<h1>Velkommen til Fog Carporte.</h1>");
        builder.append("<p>For at fortsætte med din kundekonto, skal du først bekræfte din registration.</p>");
        builder.append(String.format("<p>Klik <a href='localhost/fog/account?action=confirm-membership&id=%d&token=%s'>her</a> for at bekræfte din registration.</p>", tokenId, token));
        builder.append(String.format("<p>Hvis du ikke ønsker at tilmelde dig, kan du istedet klikke <a href='localhost/fog/account?action=reject-membership&id=%d&token=%s'>her</a></p>", tokenId, token));
        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }
}
