package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;

/**
 * The email sent to a customer after successfully placing an order.
 */
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
     * @param websiteContext Information about the fog website.
     * @return The subject of the email.
     */
    @Override public String getSubject(WebsiteContext websiteContext)
    {
        return "Din order bliver behandlet.";
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

        builder.append("<p>Vi har modtaget din ordre og behandler den nu.");
        builder.append("<p>Du kan se din ordre <a target='_blank' href='");
        builder.append(websiteContext.getBase());
        builder.append("order?id=");
        builder.append(order.getId());
        builder.append("'>her</a></p>");

        return builder.toString();
    }
}

