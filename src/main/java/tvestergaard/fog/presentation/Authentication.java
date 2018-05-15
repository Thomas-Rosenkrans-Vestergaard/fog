package tvestergaard.fog.presentation;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.employees.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

public class Authentication
{

    private final HttpServletRequest request;
    private final HttpSession        session;

    public Authentication(HttpServletRequest request)
    {
        this.request = request;
        this.session = request.getSession();
    }

    public boolean isCustomer()
    {
        return getCustomer() != null;
    }

    public Customer getCustomer()
    {
        return (Customer) session.getAttribute("customer");
    }

    /**
     * Redirects to the login page if the customer is not authenticated.
     *
     * @param response
     * @return {@code true} if the customer was redirected.
     * @throws IOException
     */
    public boolean redirect(HttpServletResponse response) throws IOException
    {
        if (!isCustomer()) {
            Notifications notifications = notifications(request);
            notifications.warning("Du skal authentificeres før, du kan tilgå denne side.");
            response.sendRedirect("authenticate?from=" + URLEncoder.encode(getFromUrl(request), "UTF-8"));
            return true;
        }

        return false;
    }

    private String getFromUrl(HttpServletRequest req)
    {
        String servletPath = req.getServletPath();
        String relative    = servletPath.replaceAll("^/", "");
        String queryString = req.getQueryString();

        if (queryString == null)
            return relative;

        return relative + '?' + queryString;
    }

    public boolean isEmployee()
    {
        return getEmployee() != null;
    }

    public Employee getEmployee()
    {
        return (Employee) session.getAttribute("employee");
    }
}
