package tvestergaard.fog.logic.customers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.logic.email.ApplicationEmail;
import tvestergaard.fog.logic.email.ApplicationMailer;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;

/**
 * Sends an email to the provided customer, so that the customer can reset their password.
 */
public class PasswordResetter
{

    /**
     * The customer dao used to access the customer data storage.
     */
    private final CustomerDAO       customerDAO;

    /**
     *
     */
    private final ApplicationMailer mailer;

    public PasswordResetter(CustomerDAO customerDAO, ApplicationMailer mailer)
    {
        this.customerDAO = customerDAO;
        this.mailer = mailer;
    }

    public void reset(String email) throws DataAccessException, InactiveCustomerException
    {
        Customer customer = customerDAO.first(where(eq(EMAIL, email)));

        if (customer == null)
            throw new InactiveCustomerException();

        ApplicationEmail resetEmail = new PasswordResetEmail(customer);
        mailer.send(resetEmail);
    }
}
