package tvestergaard.fog.logic.orders;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
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
     * Builds the Email instance that can be sent using the SimpleJavaMail library.
     *
     * @return The email instance.
     */
    @Override public Email build()
    {
        return new EmailBuilder().to(order.getCustomer().getName(), order.getCustomer().getEmail())
                                 .from("Fog carporte", "fog.carporte@gmail.com")
                                 .subject("Vi har modtaget din ordre.")
                                 .textHTML(generateHTML())
                                 .build();
    }

    private String generateHTML()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Vi har modtaget din ordre.</h1>");

        return builder.toString();
    }
}

