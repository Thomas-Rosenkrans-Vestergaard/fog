package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenPair;

public class CustomerVerificationEmail implements ApplicationEmail
{

    /**
     * The customer to send the registration confirmation email to.
     */
    private final Customer customer;

    /**
     * The secret component of the token.
     */
    private final TokenPair token;

    /**
     * Creates a new {@link CustomerVerificationEmail}.
     *
     * @param customer The customer to send the registration confirmation email to.
     * @param token    The token secret to send in the email.
     */
    public CustomerVerificationEmail(Customer customer, TokenPair token)
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
        return "Bekrædt medlemsskab";
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

        builder.append("<p>For at fortsætte med din kundekonto, skal du først bekræfte din registration.</p>");
        builder.append("<p>Klik <a target='_blank' href='");
        builder.append(websiteContext.getBase());
        builder.append("fog/confirm-membership?id=");
        builder.append(token.id);
        builder.append("&token=");
        builder.append(token.secret);
        builder.append("'> her</a> for at fortsætte.</p>");

        return builder.toString();
    }
}
