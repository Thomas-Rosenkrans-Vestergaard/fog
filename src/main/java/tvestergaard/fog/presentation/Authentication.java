package tvestergaard.fog.presentation;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.employees.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authentication
{

    private final HttpSession session;

    public Authentication(HttpServletRequest request)
    {
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

    public boolean isEmployee()
    {
        return getEmployee() != null;
    }

    public Employee getEmployee()
    {
        return (Employee) session.getAttribute("employee");
    }
}
