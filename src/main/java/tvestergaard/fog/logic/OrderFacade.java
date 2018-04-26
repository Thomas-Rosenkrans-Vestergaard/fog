package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedSpecification;
import tvestergaard.fog.logic.OrderValidationException.Reason;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.logic.OrderValidationException.Reason.*;

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
    public List<Order> get(Constraint<OrderColumn>... constraints) throws ApplicationException
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
    public Order first(Constraint<OrderColumn>... constraints) throws ApplicationException
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
     * @throws OrderValidationException When the provided details are considered invalid.
     * @throws ApplicationException     When an exception occurs during the operation.
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
            ShedSpecification shed) throws OrderValidationException
    {
        try {

            Set<Reason> reasons = validateCreateOrder(customer, cladding, width, length, height, roofing, slope, shed);
            if (!reasons.isEmpty())
                throw new OrderValidationException(reasons);

            return dao.create(customer, cladding, width, length, height, roofing, slope, rafters, shed);
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
     * @see OrderFacade#create(int, int, int, int, int, int, int, RafterChoice, ShedSpecification)
     */
    private Set<Reason> validateCreateOrder(
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
    private Set<Reason> validateOrderInformation(
            int customer,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            ShedSpecification shed)
    {
        Set<Reason> reasons = new HashSet<>();

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
    public boolean update(Order order) throws OrderValidationException
    {
        try {
            Set<Reason> reasons = validateUpdateOrder(order);
            if (!reasons.isEmpty())
                throw new OrderValidationException(reasons);

            return dao.update(order);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Validates the provided details for updating an order.
     *
     * @param order The order to validate.
     * @return Any reasons why the provided information is invalid.
     * @throws OrderValidationException When the provided details are considered invalid.
     */
    private Set<Reason> validateUpdateOrder(Order order)
    {
        // TODO: validate that customer, cladding, roofing and shed are all active
        Shed shed = order.getShed();
        Set<Reason> reasons = validateOrderInformation(
                order.getCustomer().getId(),
                order.getCladding().getId(),
                order.getWidth(),
                order.getLength(),
                order.getHeight(),
                order.getRoofing().getId(),
                order.getSlope(),
                new ShedSpecification(shed.getWidth(), shed.getDepth(), shed.getCladding().getId(),
                        shed.getFlooring().getId()));
        return reasons;
    }
}
