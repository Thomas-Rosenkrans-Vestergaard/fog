package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderRecord implements Order
{

    /**
     * The unique identifier of the order.
     */
    private final int id;

    private int customerId;

    /**
     * The customer who placed the order.
     */
    private Customer customer;

    /**
     * The width of the order.
     */
    private int width;

    /**
     * The length of the order.
     */
    private int length;

    /**
     * The height of the order.
     */
    private int height;

    /**
     * The roofing used on the order.
     */
    private Roofing roofing;

    private int roofingId;

    /**
     * The slope of the roofing.
     */
    private int slope;

    /**
     * The type of rafters chosen by the customer who placed the order.
     */
    private RafterChoice rafters;

    /**
     * The shed included with the order.
     */
    private ShedBlueprint shedBlueprint;
    private ShedUpdater   shedUpdater;
    private Shed          shed;

    /**
     * Whether or not the order is considered active.
     */
    private boolean active;

    /**
     * The number of offers that have been made regarding this order.
     */
    private final int numberOfOffers;
    private final int numberOfOpenOffers;

    /**
     * The moment when the order was placed.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new {@link OrderRecord}.
     *
     * @param id             The unique identifier of the order.
     * @param customerId     The id of the customer who placed the order.
     * @param customer       The customer who placed the order.
     * @param width          The width of the order.
     * @param length         The length of the order.
     * @param height         The height of the order.
     * @param roofingId      The id of the roofing used on the order.
     * @param roofing        The roofing used on the order.
     * @param slope          The slope of the roofing.
     * @param rafters        The type of rafters chosen by the customer who placed the order.
     * @param shedBlueprint  The shed included with the order.
     * @param active         Whether or not the order is considered active.
     * @param numberOfOffers The number of offers that have been made regarding this order.
     * @param createdAt      The time when the order was placed.
     */
    public OrderRecord(int id,
                       int customerId,
                       Customer customer,
                       int width,
                       int length,
                       int height,
                       int roofingId,
                       Roofing roofing,
                       int slope,
                       RafterChoice rafters,
                       ShedBlueprint shedBlueprint,
                       ShedUpdater shedUpdater,
                       Shed shed,
                       boolean active,
                       int numberOfOffers,
                       int numberOfOpenOffers,
                       LocalDateTime createdAt)
    {
        this.id = id;
        this.customerId = customerId;
        this.customer = customer;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofingId = roofingId;
        this.roofing = roofing;
        this.slope = slope;
        this.rafters = rafters;
        this.shedBlueprint = shedBlueprint;
        this.shedUpdater = shedUpdater;
        this.shed = shed;
        this.numberOfOffers = numberOfOffers;
        this.numberOfOpenOffers = numberOfOpenOffers;
        this.active = active;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return The unique identifier of the order.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the customer who placed the order.
     *
     * @return The customer who placed the order.
     */
    @Override public Customer getCustomer()
    {
        return customer;
    }

    @Override public int getCustomerId()
    {
        return this.customerId;
    }

    /**
     * Returns the width of the order.
     *
     * @return The width of the order.
     */
    @Override public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the order.
     *
     * @param width The new width.
     */
    @Override public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the length of the order.
     *
     * @return The length of the order.
     */
    @Override public int getLength()
    {
        return length;
    }

    /**
     * Sets the length of the order.
     *
     * @param length The new length.
     */
    @Override public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * Returns the height of the order.
     *
     * @return The height of the order.
     */
    @Override public int getHeight()
    {
        return height;
    }

    /**
     * Sets the height of the order.
     *
     * @param height The new height.
     */
    @Override public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Returns the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    @Override public Roofing getRoofing()
    {
        return roofing;
    }

    @Override public int getRoofingId()
    {
        return this.roofingId;
    }

    /**
     * Returns the slope of the roofing.
     *
     * @return The slope of the roofing.
     */
    @Override public int getSlope()
    {
        return slope;
    }

    /**
     * Sets the slope of the roofing.
     *
     * @param slope The new slope.
     */
    @Override public void setSlope(int slope)
    {
        this.slope = slope;
    }

    /**
     * Returns the choice of rafters made by the customer who placed the order.
     *
     * @return The choice of rafters made by the customer who placed the order.
     */
    @Override public RafterChoice getRafterChoice()
    {
        return rafters;
    }

    /**
     * Returns the shed included in the order. {@code null} means no shed is included.
     *
     * @return The shed included in the order.
     */
    @Override public ShedBlueprint getShedBlueprint()
    {
        return this.shedBlueprint;
    }

    /**
     * Returns the shed updater.
     *
     * @return The shed updater.
     */
    @Override public ShedUpdater getShedUpdater()
    {
        return shedUpdater;
    }

    /**
     * Returns the shed included with the order.
     *
     * @return The shed instance.
     */
    @Override public Shed getShed()
    {
        return shed;
    }

    /**
     * Returns the number of offers that have been made regarding this order.
     *
     * @return The number of offsets that have been made regarding this order.
     */
    @Override public int getNumberOfOffers()
    {
        return numberOfOffers;
    }

    /**
     * Returns the number of open offers regarding the order.
     *
     * @return The number of open offers regarding the order.
     */
    @Override public int getNumberOfOpenOffers()
    {
        return numberOfOpenOffers;
    }

    /**
     * Returns whether or not the order is considered active.
     *
     * @return {@code true} if the order is considered active.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Returns a {@code LocalDateTime} representing the moment in time when the customer placed the {@link Order}.
     *
     * @return The {@code LocalDateTime}.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return getId() == that.getId() &&
                getWidth() == that.getWidth() &&
                getLength() == that.getLength() &&
                getHeight() == that.getHeight() &&
                getSlope() == that.getSlope() &&
                Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getRoofing(), that.getRoofing()) &&
                getRafterChoice() == that.getRafterChoice() &&
                Objects.equals(getShedBlueprint(), that.getShedBlueprint()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
