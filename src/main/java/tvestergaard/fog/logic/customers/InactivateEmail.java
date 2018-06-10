package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;

public class InactivateEmail implements ApplicationEmail
{

    /**
     * The customer the email is sent to.
     */
    private final Customer customer;

    /**
     * Creates a new {@link InactivateEmail}.
     *
     * @param customer The customer the email is sent to.
     */
    public InactivateEmail(Customer customer)
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
        return "Din konto er blevet deaktiveret.";
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
        builder.append("Din kundekonto er blevet deaktiveret.");
        builder.append("</p>");

        return builder.toString();
    }
}
