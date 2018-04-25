package tvestergaard.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
}
