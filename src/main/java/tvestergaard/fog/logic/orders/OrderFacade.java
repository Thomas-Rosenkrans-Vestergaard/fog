package tvestergaard.fog.logic.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class OrderFacade
{

    private final OrderDAO       dao;
    private final OrderValidator validator;

    private final CustomerDAO customerDAO;
    private final CladdingDAO claddingDAO;
    private final RoofingDAO  roofingDAO;
    private final FlooringDAO flooringDAO;

    public OrderFacade(OrderDAO dao, OrderValidator validator, CustomerDAO customerDAO, CladdingDAO claddingDAO, RoofingDAO roofingDAO, FlooringDAO flooringDAO)
    {
        this.dao = dao;
        this.validator = validator;
        this.customerDAO = customerDAO;
        this.claddingDAO = claddingDAO;
        this.roofingDAO = roofingDAO;
        this.flooringDAO = flooringDAO;
    }

    public OrderFacade()
    {
        MysqlDataSource source = ProductionDataSource.getSource();
        this.dao = new MysqlOrderDAO(source);
        this.validator = new OrderValidator();
        this.customerDAO = new MysqlCustomerDAO(source);
        this.claddingDAO = new MysqlCladdingDAO(source);
        this.roofingDAO = new MysqlRoofingDAO(source);
        this.flooringDAO = new MysqlFlooringDAO(source);
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
        try {

            Set<OrderError> reasons = validator.validate(customer, cladding, width, length, height, roofing, slope, shed);
            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);


            return dao.create(OrderBlueprint.from(
                    customer,
                    cladding,
                    width,
                    length,
                    height,
                    roofing,
                    slope,
                    rafters,
                    shed == null ? null : ShedBlueprint.from(shed.getWidth(), shed.getDepth(), shed.getCladdingId(), shed.getFlooringId())));
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

    public boolean update(int id,
                          int cladding,
                          int width,
                          int length,
                          int height,
                          int roofing,
                          int slope,
                          RafterChoice rafters,
                          ShedSpecification shed) throws OrderValidatorException
    {
        try {
            Set<OrderError> reasons = validator.validate(-1, cladding, width, length, height, roofing, slope, shed);
            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);


            return dao.update(OrderUpdater.from(id, -1, cladding, width, length, height, roofing, slope, rafters, ShedBlueprint.from(shed.getWidth(), shed.getDepth(), shed.getCladdingId(), shed.getFlooringId())));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
