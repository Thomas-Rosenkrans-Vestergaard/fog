package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.UnknownCustomerException;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderBlueprint;
import tvestergaard.fog.data.orders.OrderDAO;
import tvestergaard.fog.data.orders.ShedBlueprint;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.email.ApplicationMailer;

import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;

/**
 * Places orders for some customer.
 */
public class OrderPlacer
{

    /**
     * The order dao used to persist new orders.
     */
    private final OrderDAO orderDAO;

    /**
     * The customer dao used to validate the customer placing new orders.
     */
    private final CustomerDAO customerDAO;

    /**
     * The object responsible for validating the information about the order.
     */
    private final OrderValidator validator;

    /**
     * The object responsible for sending order confirmation emails to the customer.
     */
    private final ApplicationMailer mailer;

    /**
     * Creates a new {@link OrderPlacer}.
     *
     * @param orderDAO    The order dao used to persist new orders.
     * @param customerDAO The customer dao used to validate the customer placing new orders.
     * @param mailer      The object responsible for sending order confirmation emails to the customer.
     */
    public OrderPlacer(OrderDAO orderDAO, CustomerDAO customerDAO, ApplicationMailer mailer)
    {
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
        this.validator = new OrderValidator();
        this.mailer = mailer;
    }

    /**
     * Places a new order using the provided information.
     *
     * @param customerId The id of the customer placing the order.
     * @param width      The width of the order in centimeters.
     * @param length     The length of the order in centimeters.
     * @param height     The height of the order in centimeters.
     * @param roofing    The roofing to be applied to the order.
     * @param slope      The slope of the roofing.
     * @param shed       The shed to include in the order.
     * @param comment    The comment provided by the customer about the order.
     * @return The object representing the newly created order.
     * @throws OrderValidatorException     When the provided information is considered invalid.
     * @throws UnknownCustomerException    When the customer placing the order is unknown to the application.
     * @throws InactiveCustomerException   When the customer is inactive, and can therefor not place new orders.
     * @throws UnverifiedCustomerException When the customer has not confirmed their email address, and can therefor
     *                                     not place new orders.
     * @throws DataAccessException         When a data storage exception occurs while performing the operation.
     */
    public Order place(
            int customerId,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            ShedBlueprint shed,
            String comment) throws OrderValidatorException,
                                   UnknownCustomerException,
                                   InactiveCustomerException,
                                   UnverifiedCustomerException,
                                   DataAccessException
    {
        Set<OrderError> reasons = validator.validate(width, length, height, slope, shed);

        if (!reasons.isEmpty())
            throw new OrderValidatorException(reasons);

        if (!reasons.isEmpty())
            throw new OrderValidatorException(reasons);

        Customer customer = customerDAO.first(where(eq(CustomerColumn.ID, customerId)));

        if (customer == null)
            throw new UnknownCustomerException();

        if (!customer.isActive())
            throw new InactiveCustomerException();

        if (!customer.isVerified())
            throw new UnverifiedCustomerException();

        Order order = orderDAO.create(OrderBlueprint.from(
                customerId,
                width,
                length,
                height,
                roofing,
                slope,
                true,
                shed,
                comment));

        OrderConfirmationEmail email = new OrderConfirmationEmail(order);
        mailer.send(email);
        return order;
    }
}
