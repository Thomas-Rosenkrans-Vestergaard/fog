package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenPair;

public class RegistrationEmail implements ApplicationEmail
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
     * Creates a new {@link RegistrationEmail}.
     *
     * @param customer The customer to send the registration confirmation email to.
     * @param token    The token secret to send in the email.
     */
    public RegistrationEmail(Customer customer, TokenPair token)
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
     * @return The subject of the email.
     */
    @Override public String getSubject()
    {
        return "Bekrædt medlemsskab";
    }

    /**
     * Returns the html contents of the email.
     *
     * @return The html contents of the email.
     */
    @Override public String getHtmlContents()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<p>For at fortsætte med din kundekonto, skal du først bekræfte din registration.</p>");
        builder.append("<p>Klik <a href='http://localhost/fog/confirm-membership?id=");
        builder.append(token.id);
        builder.append("&token=");
        builder.append(token.secret);
        builder.append("'> her for at fortsætte</a></p>");
        return builder.toString();
    }
}
