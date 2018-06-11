package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;

/**
 * The email sent to customers when their account is activated.
 */
public class CustomerActivationEmail implements ApplicationEmail
{

    /**
     * The recipient of the activation email.
     */
    private final Customer customer;

    /**
     * Creates a new {@link CustomerActivationEmail}.
     *
     * @param customer The recipient of the activation email.
     */
    public CustomerActivationEmail(Customer customer)
    {
        this.customer = customer;
    }

    /**
     * Returns the customer to receive the email.
     *
     * @return The customer to receive the email.
     */
    @Override public Customer getRecipient()
    {
        return null;
    }

    /**
     * Returns the subject of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The subject of the email.
     */
    @Override public String getSubject(WebsiteContext websiteContext)
    {
        return "Din konto er blevet aktiveret.";
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

        builder.append("<p>");
        builder.append("Din kundekonto er blevet aktiveret.");
        builder.append("</p>");

        return builder.toString();
    }
}
