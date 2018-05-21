package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenPair;

public class ForgotPasswordEmail implements ApplicationEmail
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
     * Creates a new {@link ForgotPasswordEmail}.
     *
     * @param customer The customer to send the password-reset email to.
     * @param token    The token to send within the password-reset email.
     */
    public ForgotPasswordEmail(Customer customer, TokenPair token)
    {
        this.customer = customer;
        this.token = token;
    }

    /**
     * Returns the customer to receive the email.
     *
     * @return The customer to receive the email.
     */
    @Override public Customer getRecipient()
    {
        return customer;
    }

    /**
     * Returns the subject of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The subject of the email.
     */
    @Override public String getSubject(WebsiteContext websiteContext)
    {
        return "Glemt adgangskode";
    }

    /**
     * Returns the html contents of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The html contents of the email.
     */
    @Override public String getHtmlContents(WebsiteContext websiteContext)
    {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>Hvis du ønsker et nyt password, kan du klikke ");
        builder.append("<a href='");
        builder.append(websiteContext.getBase());
        builder.append("reset-password?tokenId=");
        builder.append(token.id);
        builder.append("&tokenSecret=");
        builder.append(token.secret);
        builder.append("'>");
        builder.append("her</a>.</p>");

        return builder.toString();
    }
}
