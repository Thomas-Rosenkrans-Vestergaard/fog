package tvestergaard.fog.presentation;

import tvestergaard.fog.data.customers.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authentication
{

    private final HttpSession session;

    public Authentication(HttpServletRequest request)
    {
        this.session = request.getSession();
    }

    public boolean isAuthenticated()
    {
        return getCustomer() != null;
    }

    public Customer getCustomer()
    {
        return (Customer) session.getAttribute("customer");
    }
}
