package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.orders.Order;

import java.time.LocalDateTime;

public class OfferRecord implements Offer
{

    private final int           id;
    private final Order         order;
    private final int           orderId;
    private final Employee      employee;
    private final int           employeeId;
    private final int           price;
    private       OfferStatus   status;
    private final LocalDateTime createdAt;

    public OfferRecord(int id, Order order, int orderId, Employee employee, int employeeId, int price, OfferStatus status, LocalDateTime createdAt)
    {
        this.id = id;
        this.order = order;
        this.orderId = orderId;
        this.employee = employee;
        this.employeeId = employeeId;
        this.price = price;
        this.createdAt = createdAt;
        this.status = status;
    }

    /**
     * Returns the unique identifier of the offer.
     *
     * @return The unique identifier of the offer.
     */
    @Override public int getId()
    {
        return id;
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
     * Returns the current status of the offer.
     *
     * @return The current status of the offer.
     */
    @Override public OfferStatus getStatus()
    {
        return status;
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

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferRecord that = (OfferRecord) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (employeeId != that.employeeId) return false;
        if (price != that.price) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
        if (status != that.status) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override public int hashCode()
    {
        int result = id;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + orderId;
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + employeeId;
        result = 31 * result + price;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "OfferRecord{" +
               "id=" + id +
               ", order=" + order +
               ", orderId=" + orderId +
               ", employee=" + employee +
               ", employeeId=" + employeeId +
               ", price=" + price +
               ", status=" + status +
               ", createdAt=" + createdAt +
               '}';
    }
}
