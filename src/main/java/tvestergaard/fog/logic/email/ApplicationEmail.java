package tvestergaard.fog.logic.email;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.WebsiteContext;

public interface ApplicationEmail
{

    /**
     * Returns the customer to receive the email.
     *
     * @return The customer to receive the email.
     */
    Customer getRecipient();

    /**
     * Returns the subject of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The subject of the email.
     */
    String getSubject(WebsiteContext websiteContext);

    /**
     * Returns the html contents of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The html contents of the email.
     */
    String getHtmlContents(WebsiteContext websiteContext);
}
