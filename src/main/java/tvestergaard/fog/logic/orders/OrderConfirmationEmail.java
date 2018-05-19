package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.logic.email.ApplicationEmail;

public class OrderConfirmationEmail implements ApplicationEmail
{

    /**
     * The order from which to create the confirmation email.
     */
    private final Order order;

    /**
     * Creates a new {@link OrderConfirmationEmail}.
     *
     * @param order The order from which to create the confirmation email.
     */
    public OrderConfirmationEmail(Order order)
    {
        this.order = order;
    }

    /**
     * Returns the customer to receive the email.
     *
     * @return The customer to receive the email.
     */
    @Override public Customer getRecipient()
    {
        return order.getCustomer();
    }

    /**
     * Returns the subject of the email.
     *
     * @return The subject of the email.
     */
    @Override public String getSubject()
    {
        return "Din order bliver behandlet.";
    }

    /**
     * Returns the html contents of the email.
     *
     * @return The html contents of the email.
     */
    @Override public String getHtmlContents()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<p>Vi har modtaget din ordre og behandler den nu.");
        builder.append("<p>Du kan se din order <a href=''></a></p>");
        return builder.toString();
    }
}

