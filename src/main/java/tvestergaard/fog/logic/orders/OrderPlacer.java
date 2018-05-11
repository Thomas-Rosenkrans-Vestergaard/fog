package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerColumn;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.UnknownCustomerException;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.InactiveCustomerException;

import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;

public class OrderPlacer
{

    private final OrderDAO       orderDAO;
    private final CustomerDAO    customerDAO;
    private final OrderValidator validator;

    public OrderPlacer(OrderDAO orderDAO, CustomerDAO customerDAO, OrderValidator validator)
    {
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
        this.validator = validator;
    }

    public Order place(
            int customerId,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            RafterChoice rafters,
            ShedSpecification shed) throws OrderValidatorException, UnknownCustomerException, InactiveCustomerException, UnconfirmedCustomerException
    {
        try {

            Set<OrderError> reasons = validator.validate(customerId, width, length, height, roofing, slope, shed);

            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            if (!reasons.isEmpty())
                throw new OrderValidatorException(reasons);

            Customer customer = customerDAO.first(where(eq(CustomerColumn.ID, customerId)));

            if (customer == null)
                throw new UnknownCustomerException();

            if (!customer.isActive())
                throw new InactiveCustomerException();

            if (!customer.isConfirmed())
                throw new UnconfirmedCustomerException();

            return orderDAO.create(OrderBlueprint.from(
                    customerId,
                    width,
                    length,
                    height,
                    roofing,
                    slope,
                    rafters,
                    ShedBlueprint.from(shed.getDepth(), shed.getCladdingId(), shed.getFlooringId())));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
