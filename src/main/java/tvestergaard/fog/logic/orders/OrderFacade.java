package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.email.ApplicationMailer;

import java.util.List;
import java.util.Set;

public class OrderFacade
{

    /**
     * The dao used to access the order storage.
     */
    private final OrderDAO dao;

    /**
     * The validator used to validate orders to create.
     */
    private final OrderValidator validator;

    /**
     * The object responsible for sending {@link OrderConfirmationEmail}s.
     */
    private final ApplicationMailer mailer;

    /**
     * Creates a new {@link OrderFacade}.
     *
     * @param dao    The dao used to access the order storage.
     * @param mailer The object responsible for sending {@link OrderConfirmationEmail}s.
     */
    public OrderFacade(OrderDAO dao, ApplicationMailer mailer)
    {
        this.dao = dao;
        this.mailer = mailer;
        this.validator = new OrderValidator();
    }

    /**
     * Returns the orders in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Order> get(Constraint<OrderColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Order first(Constraint<OrderColumn>... constraints)
    {
        try {
            return dao.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Creates a new order.
     *
     * @param customer The id of the customer who placed the order to create.
     * @param width    The width of the order to create.
     * @param length   The length of the order to create.
     * @param height   The height of the order to create.
     * @param roofing  The id of the roofing used on the order to create.
     * @param slope    The slope of the roofing used on the order to create.
     * @param rafters  The rafters construction delivered with the order to create.
     * @param shed     The shed to add to the order.
     * @return The new order.
     * @throws OrderValidatorException When the provided details are considered invalid.
     * @throws ApplicationException    When an exception occurs during the operation.
     */
    public Order create(
            int customer,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            RafterChoice rafters,
            ShedSpecification shed) throws OrderValidatorException, InactiveCustomerException, UnconfirmedCustomerException
    {
        try {

            Set<OrderError> reasons = validator.validate(customer, width, length, height, roofing, slope, shed);
            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            Order order = dao.create(OrderBlueprint.from(
                    customer,
                    width,
                    length,
                    height,
                    roofing,
                    slope,
                    rafters,
                    shed == null ? null : ShedBlueprint.from(shed.getDepth(), shed.getCladdingId(), shed.getFlooringId())));

            OrderConfirmationEmail email = new OrderConfirmationEmail(order);
            mailer.send(email);
            return order;

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the number of orders that are both active, and have not yet received any offers.
     *
     * @return The number of such orders.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */

    public int getNumberOfNewOrders()
    {
        try {
            return dao.getNumberOfNewOrders();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the order with the provided id.
     *
     * @param id      The id of the oder to update.
     * @param width   The new width of the order.
     * @param length  The new length of the order.
     * @param height  The new height of the order.
     * @param roofing The new roofing of the order.
     * @param slope   The new slope of the roofing on the order.
     * @param rafters The rafter choice on the order.
     * @param shed    The shed built into the order.
     * @return {@code true} if the order was updated.
     * @throws OrderValidatorException When the provided information is not valid.
     */
    public boolean update(int id,
                          int width,
                          int length,
                          int height,
                          int roofing,
                          int slope,
                          RafterChoice rafters,
                          ShedSpecification shed) throws OrderValidatorException
    {
        try {
            Set<OrderError> reasons = validator.validate(-1, width, length, height, roofing, slope, shed);
            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            return dao.update(OrderUpdater.from(id, -1, width, length, height, roofing, slope, rafters,
                    shed == null ? null : ShedBlueprint.from(shed.getDepth(), shed.getCladdingId(), shed.getFlooringId())));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the number of orders in the data storage.
     *
     * @return The number of orders in the data storage.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public int size() throws ApplicationException
    {
        try {
            return dao.size();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
