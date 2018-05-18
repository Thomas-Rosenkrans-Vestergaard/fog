package tvestergaard.fog.logic.offers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.tokens.TokenPair;

public class OfferEmail implements ApplicationEmail
{

    /**
     * The offer to send in the email.
     */
    private final Offer offer;

    /**
     * The secret token to include in the email as links.
     */
    private final TokenPair tokenSecret;

    /**
     * Creates a new {@link OfferEmail}.
     *
     * @param offer       The offer to send in the email.
     * @param tokenSecret The secret token to include in the email as links.
     */
    public OfferEmail(Offer offer, TokenPair tokenSecret)
    {
        this.offer = offer;
        this.tokenSecret = tokenSecret;
    }

    /**
     * Builds the Email instance that can be sent using the SimpleJavaMail library.
     *
     * @return The email instance.
     */
    @Override public Email build()
    {
        Customer customer = offer.getOrder().getCustomer();

        return new EmailBuilder()
                .to(customer.getName(), customer.getEmail())
                .from("Fog carporte", "fog.carporte@gmail.com")
                .subject("Her er et tilbud på din ordre.")
                .textHTML(generateHTML())
                .build();
    }

    /**
     * Generates the html for the registration confirmation email.
     *
     * @return The html for the registration confirmation email.
     */
    private String generateHTML()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Her er et tilbud på din ordre.</h1>");
        builder.append("<p>Vi tilbyder prisen: " + offer.getPrice() + " øre</p>");
        builder.append(String.format("<p>For at accepterer: fog/api?action=accept-offer&offer=%d&tokenId=%d&tokenSecret=%s</p>",
                offer.getId(),
                tokenSecret.id,
                tokenSecret.secret));
        builder.append(String.format("<p>For at afvise: fog/api?action=reject-offer&offer=%d&tokenId=%d&tokenSecret=%s</p>",
                offer.getId(),
                tokenSecret.id,
                tokenSecret.secret));

        return builder.toString();
    }
}
