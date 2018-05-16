package tvestergaard.fog.presentation;

import tvestergaard.fog.logic.customers.CustomerError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.logic.customers.CustomerError.*;

public class PresentationFunctions
{

    /**
     * Returns the current, or creates a new, notifications instance for the session of the provided request. Sets the
     * notifications instance as an attribute on the provided request.
     *
     * @param request The request.
     * @return The resulting notifications instance.
     */
    public static Notifications notifications(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object      o       = session.getAttribute("notifications");

        if (o == null) {
            Notifications notifications = new Notifications();
            session.setAttribute("notifications", notifications);
            return notifications;
        }

        return (Notifications) o;
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    /**
     * Formats the provided {@code LocalDateTime} to the format dd-mm-yyyy HH:mm.
     *
     * @param datetime The {@code LocalDateTime} to format.
     * @return The resulting formatted string.
     */
    public static String formatDatetime(LocalDateTime datetime)
    {
        return datetime.format(formatter);
    }

    public static String formatBoolean(Boolean value)
    {
        return value ? "Ja" : "Nej";
    }

    /**
     * Formats the provided price in cents to dollars using a period as the decimal separator.
     *
     * @param priceInCents The price in cents.
     * @return The price in dollars.
     */
    public static String formatPrice(Integer priceInCents)
    {
        int cents = priceInCents % 100;
        int dkk   = (priceInCents - cents) / 100;

        if (cents == 0)
            return Integer.toString(dkk) + " kr.";

        String result = dkk + "." + (cents < 9 ? "0" + cents : cents) + " kr.";

        return result;
    }

    private static Map<CustomerError, String> customerErrors;

    public static String getError(CustomerError error)
    {
        if (customerErrors == null) {
            customerErrors = new HashMap<>();
            customerErrors.put(NAME_EMPTY, "Det sendte navn må ikke være tomt.");
            customerErrors.put(NAME_LONGER_THAN_255, "Det sendte navn er for langt.");
            customerErrors.put(ADDRESS_EMPTY, "Den sendte adresse må ikke være tom.");
            customerErrors.put(ADDRESS_LONGER_THAN_255, "Den sendte adresse er for lang.");
            customerErrors.put(EMAIL_INVALID, "Den sendte email er ikke formateret korrekt.");
            customerErrors.put(EMAIL_TAKEN, "Den sendte email er allerede i bruge  på siden.");
            customerErrors.put(EMAIL_LONGER_THAN_255, "Den sendte email er for lang.");
            customerErrors.put(PHONE_EMPTY, "Det sendte telefonnummer må ikke være tomt.");
            customerErrors.put(PHONE_LONGER_THAN_30, "Det sendte telefonnummer er for langt.");
            customerErrors.put(PASSWORD_SHORTER_THAN_4, "Det sendte password er for kort.");
        }

        return customerErrors.get(error);
    }
}
