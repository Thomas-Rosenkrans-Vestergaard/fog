package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.orders.Order;

import java.time.LocalDateTime;

public class OfferRecord implements Offer
{

    private final Order         order;
    private final int           orderId;
    private final Employee      employee;
    private final int           employeeId;
    private final int           price;
    private final LocalDateTime createdAt;

    public OfferRecord(Order order, int orderId, Employee employee, int employeeId, int price, LocalDateTime createdAt)
    {
        this.order = order;
        this.orderId = orderId;
        this.employee = employee;
        this.employeeId = employeeId;
        this.price = price;
        this.createdAt = createdAt;
    }

    /**
     * Returns the id of the order that the offer was issued for.
     *
     * @return The id of the order that the offer was issued for.
     */
    @Override public int getOrderId()
    {
        return orderId;
    }

    /**
     * Returns the id of the employee who created the offer.
     *
     * @return The id of the employee who created the offer.
     */
    @Override public int getEmployeeId()
    {
        return employeeId;
    }

    /**
     * Returns the price of the offer.
     *
     * @return The price of the offer.
     */
    @Override public int getPrice()
    {
        return price;
    }

    /**
     * Returns the order the offer was issued for.
     *
     * @return The order the offer was issued for.
     */
    @Override public Order getOrder()
    {
        return order;
    }

    /**
     * Returns the employee who created the offer.
     *
     * @return The employee who created the offer.
     */
    @Override public Employee getEmployee()
    {
        return employee;
    }

    /**
     * Returns the moment in time when the offer was created.
     *
     * @return The moment in time when the offer was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
}
