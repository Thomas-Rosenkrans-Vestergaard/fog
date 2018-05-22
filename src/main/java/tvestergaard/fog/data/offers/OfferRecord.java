package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.orders.Order;

import java.time.LocalDateTime;
import java.util.Objects;

public class OfferRecord implements Offer
{

    /**
     * The unique identifier of the offer.
     */
    private final int id;

    /**
     * The order the offer references.
     */
    private final Order order;

    /**
     * The id of the order the offer references.
     */
    private final int orderId;

    /**
     * The employee who created the offer.
     */
    private final Employee employee;

    /**
     * The id of the employee who created the offer.
     */
    private final int employeeId;

    /**
     * The price of the offer (in cents).
     */
    private final int price;

    /**
     * The status of the offer.
     */
    private OfferStatus status;

    /**
     * The moment in time when the offer was created.
     */
    private final LocalDateTime createdAt;

    /**
     * Creates a new {@link OfferRecord}.
     *
     * @param id         The unique identifier of the offer.
     * @param order      The order the offer references.
     * @param orderId    The id of the order the offer references.
     * @param employee   The employee who created the offer.
     * @param employeeId The id of the employee who created the offer.
     * @param price      The price of the offer (in cents).
     * @param status     The status of the offer.
     * @param createdAt  The moment in time when the offer was created.
     */
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
        if (!(o instanceof Offer)) return false;
        Offer that = (Offer) o;
        return getId() == that.getId() &&
                getOrderId() == that.getOrderId() &&
                getEmployeeId() == that.getEmployeeId() &&
                getPrice() == that.getPrice() &&
                Objects.equals(getOrder(), that.getOrder()) &&
                Objects.equals(getEmployee(), that.getEmployee()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getOrder(), getOrderId(), getEmployee(), getEmployeeId(), getPrice(), getStatus(), getCreatedAt());
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
