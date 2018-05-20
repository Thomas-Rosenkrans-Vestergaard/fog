package tvestergaard.fog.logic.offers;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.logic.WebsiteContext;
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
     * Returns the customer to receive the email.
     *
     * @return The customer to receive the email.
     */
    @Override public Customer getRecipient()
    {
        return offer.getOrder().getCustomer();
    }

    /**
     * Returns the subject of the email.
     *
     * @param websiteContext Information about the fog website.
     * @return The subject of the email.
     */
    @Override public String getSubject(WebsiteContext websiteContext)
    {
        return "Du har modtaget et nyt tilbud";
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

        builder.append("<p>Vi tilbyder prisen: " + formatPrice(offer.getPrice()) + "</p>");
        builder.append(String.format("<p>For at accepterer: <a href=''>fog/api?action=accept-offer&offer=%d&tokenId=%d&tokenSecret=%s</p>",
                offer.getId(),
                tokenSecret.id,
                tokenSecret.secret));
        builder.append(String.format("<p>For at afvise: fog/api?action=reject-offer&offer=%d&tokenId=%d&tokenSecret=%s</p>",
                offer.getId(),
                tokenSecret.id,
                tokenSecret.secret));

        return builder.toString();
    }

    /**
     * Formats the provided price in cents to dollars using a period as the decimal separator.
     *
     * @param priceInCents The price in cents.
     * @return The price in dollars.
     */
    public static String formatPrice(int priceInCents)
    {
        int cents = priceInCents % 100;
        int dkk   = (priceInCents - cents) / 100;

        if (cents == 0)
            return Integer.toString(dkk) + " kr.";

        String result = dkk + "." + (cents < 9 ? "0" + cents : cents) + " kr.";

        return result;
    }
}
