package tvestergaard.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Object o = session.getAttribute("notifications");

        if (o == null) {
            Notifications notifications = new Notifications();
            session.setAttribute("notifications", notifications);
            return notifications;
        }

        return (Notifications) o;
    }

    public static FormResponse formResponse(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object o = session.getAttribute("formResponse");

        if (o == null) {
            FormResponse response = new FormResponse();
            session.setAttribute("formResponse", response);
            return response;
        }

        return (FormResponse) o;
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
        int dkk = (priceInCents - cents) / 100;

        if (cents == 0)
            return Integer.toString(dkk);

        return dkk + "." + (cents < 9 ? "0" + cents : cents) + " kr.";
    }
}
