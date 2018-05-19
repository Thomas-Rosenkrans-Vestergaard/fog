package tvestergaard.fog.logic.email;

import tvestergaard.fog.data.customers.Customer;

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
     * @return The subject of the email.
     */
    String getSubject();

    /**
     * Returns the html contents of the email.
     *
     * @return The html contents of the email.
     */
    String getHtmlContents();
}
