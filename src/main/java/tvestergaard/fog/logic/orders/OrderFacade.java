package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.data.sheds.ShedUpdater;
import tvestergaard.fog.logic.ApplicationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.logic.orders.OrderError.*;

public class OrderFacade
{

    private final OrderDAO dao;

    public OrderFacade(OrderDAO dao)
    {
        this.dao = dao;
    }

    public OrderFacade()
    {
        this(new MysqlOrderDAO(ProductionDataSource.getSource()));
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
     * @param cladding The id of the cladding used on the order to create.
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
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            RafterChoice rafters,
            ShedSpecification shed) throws OrderValidatorException
    {
//        try {

//            OrderBlueprint blueprint = OrderBlueprint.from()
//                                               Set < OrderError > reasons = validateCreateOrder(customer, cladding, width, length, height, roofing, slope, shed);
//            if (!reasons.isEmpty())
//                throw new OrderValidatorException(reasons);
//
//            return dao.create(customer, cladding, width, length, height, roofing, slope, rafters, shed);

        return null;
//        } catch (DataAccessException e) {
//            throw new ApplicationException(e);
//        }
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
     * Validates the provided details for creating a new order.
     *
     * @param customer The customer variable to perform validation upon.
     * @param cladding The cladding variable to perform validation upon.
     * @param width    The width variable to perform validation upon.
     * @param length   The length variable to perform validation upon.
     * @param height   The height variable to perform validation upon.
     * @param roofing  The roofing variable to perform validation upon.
     * @param slope    The slope variable to perform validation upon.
     * @param shed     The shed variable to perform validation upon.
     * @return Any reasons why the provided information is invalid.
     */
    private Set<OrderError> validateCreateOrder(
            int customer,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            ShedSpecification shed)
    {

        return validateOrderInformation(customer, cladding, width, length, height, roofing, slope, shed);
    }

    /**
     * Validates the provided details for creating a new order.
     *
     * @param customer The customer variable to perform validation upon.
     * @param cladding The cladding variable to perform validation upon.
     * @param width    The width variable to perform validation upon.
     * @param length   The length variable to perform validation upon.
     * @param height   The height variable to perform validation upon.
     * @param roofing  The roofing variable to perform validation upon.
     * @param slope    The slope variable to perform validation upon.
     * @param shed     The shed variable to perform validation upon.
     * @return Any reasons why the provided information is invalid.
     */
    private Set<OrderError> validateOrderInformation(
            int customer,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            ShedSpecification shed)
    {
        Set<OrderError> reasons = new HashSet<>();

        if (width < 240 || width % 30 != 0 || width > 750)
            reasons.add(ILLEGAL_WIDTH);

        if (length < 240 || length % 30 != 0 && length > 780)
            reasons.add(ILLEGAL_LENGTH);

        if (height < 180 && length % 30 != 0 && height > 300)
            reasons.add(ILLEGAL_HEIGHT);

        if (slope < 1 && slope > 89)
            reasons.add(ILLEGAL_SLOPE);

        return reasons;
    }

    /**
     * Updates the entity in the data storage to match the provided {@code order}.
     *
     * @param order The order to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException When an exception occurs while performing the operation.
     * @see OrderFacade#update(Order)
     */
    public boolean update(Order order) throws OrderValidatorException
    {
        try {
            Set<OrderError> reasons = validate(order);
            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            return dao.update(order);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Validates the provided details for updating an order.
     *
     * @param updater The order to validate.
     * @return Any reasons why the provided information is invalid.
     * @throws OrderValidatorException When the provided details are considered invalid.
     */
    private Set<OrderError> validate(OrderUpdater updater)
    {
        // TODO: validate that customer, cladding, roofing and shed are all active
        ShedUpdater shed = updater.getShed();
        Set<OrderError> reasons = validateOrderInformation(
                updater.getCustomer().getId(),
                updater.getCladding().getId(),
                updater.getWidth(),
                updater.getLength(),
                updater.getHeight(),
                updater.getRoofing().getId(),
                updater.getSlope(),
                new ShedSpecification(shed.getWidth(), shed.getDepth(), shed.getCladding().getId(),
                        shed.getFlooring().getId()));
        return reasons;
    }
}
