package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.logic.WebsiteContext;
import tvestergaard.fog.logic.email.ApplicationEmail;

/**
 * The email sent to customers when an employee cancels one of their orders.
 */
public class OrderCanceledEmail implements ApplicationEmail
{

    /**
     * The order that was canceled.
     */
    private final Order order;

    /**
     * Creates a new {@link OrderConfirmationEmail}.
     *
     * @param order The order that was canceled.
     */
    public OrderCanceledEmail(Order order)
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
        return "Din ordre er blevet aflyst";
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

        builder.append("<p>Vi har valgt at aflyse din ordre.");
        builder.append("<p>Du kan se din order <a target='_blank' href='");
        builder.append(websiteContext.getBase());
        builder.append("order?id=");
        builder.append(order.getId());
        builder.append("'>her</a></p>");

        return builder.toString();
    }
}
