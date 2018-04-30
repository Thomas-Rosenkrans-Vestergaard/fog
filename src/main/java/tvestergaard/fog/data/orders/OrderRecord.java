package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
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
     * The cladding used on the order.
     */
    private Cladding cladding;

    private int claddingId;

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
    private Shed shed;

    /**
     * Whether or not the order is considered active.
     */
    private boolean active;

    /**
     * The number of offers that have been made regarding this order.
     */
    private int numberOfOffers;

    /**
     * The moment when the order was placed.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new {@link OrderRecord}.
     *
     * @param id             The unique identifier of the order.
     * @param customer       The customer who placed the order.
     * @param cladding       The cladding used on the order.
     * @param width          The width of the order.
     * @param length         The length of the order.
     * @param height         The height of the order.
     * @param roofing        The roofing used on the order.
     * @param slope          The slope of the roofing.
     * @param rafters        The type of rafters chosen by the customer who placed the order.
     * @param shed           The shed included with the order.
     * @param active         Whether or not the order is considered active.
     * @param numberOfOffers The number of offers that have been made regarding this order.
     * @param createdAt      The time when the order was placed.
     */
    public OrderRecord(int id,
                       int customerId,
                       Customer customer,
                       int claddingId,
                       Cladding cladding,
                       int width,
                       int length,
                       int height,
                       int roofingId,
                       Roofing roofing,
                       int slope,
                       RafterChoice rafters,
                       Shed shed,
                       boolean active,
                       int numberOfOffers,
                       LocalDateTime createdAt)
    {
        this.id = id;
        this.customerId = customerId;
        this.customer = customer;
        this.claddingId = claddingId;
        this.cladding = cladding;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofingId = roofingId;
        this.roofing = roofing;
        this.slope = slope;
        this.rafters = rafters;
        this.shed = shed;
        this.numberOfOffers = numberOfOffers;
        this.active = active;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return The unique identifier of the order.
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * Returns the customer who placed the order.
     *
     * @return The customer who placed the order.
     */
    @Override
    public Customer getCustomer()
    {
        return customer;
    }

    @Override public int getCustomerId()
    {
        return this.customerId;
    }

    /**
     * Returns the cladding used on the order.
     *
     * @return The cladding used on the order.
     */
    @Override
    public Cladding getCladding()
    {
        return cladding;
    }

    @Override
    public int getCladdingId()
    {
        return claddingId;
    }

    /**
     * Sets the cladding used on the order.
     *
     * @param cladding The new cladding.
     */
    @Override
    public void setCladdingId(int cladding)
    {
        this.claddingId = cladding;
    }

    /**
     * Returns the width of the order.
     *
     * @return The width of the order.
     */
    @Override
    public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the order.
     *
     * @param width The new width.
     */
    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the length of the order.
     *
     * @return The length of the order.
     */
    @Override
    public int getLength()
    {
        return length;
    }

    /**
     * Sets the length of the order.
     *
     * @param length The new length.
     */
    @Override
    public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * Returns the height of the order.
     *
     * @return The height of the order.
     */
    @Override
    public int getHeight()
    {
        return height;
    }

    /**
     * Sets the height of the order.
     *
     * @param height The new height.
     */
    @Override
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Returns the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    @Override
    public Roofing getRoofing()
    {
        return roofing;
    }

    @Override public int getRoofingId()
    {
        return this.roofingId;
    }

    /**
     * Sets the roofing used on the order.
     *
     * @param roofing The new roofing.
     */
    @Override
    public void setRoofingId(int roofing)
    {
        this.roofingId = roofing;
    }

    /**
     * Returns the slope of the roofing.
     *
     * @return The slope of the roofing.
     */
    @Override
    public int getSlope()
    {
        return slope;
    }

    /**
     * Sets the slope of the roofing.
     *
     * @param slope The new slope.
     */
    @Override
    public void setSlope(int slope)
    {
        this.slope = slope;
    }

    /**
     * Returns the choice of rafters made by the customer who placed the order.
     *
     * @return The choice of rafters made by the customer who placed the order.
     */
    @Override
    public RafterChoice getRafterChoice()
    {
        return rafters;
    }

    /**
     * Sets the {@link RafterChoice} chosen by the customer who placed the order.
     *
     * @param rafters The new {@link RafterChoice}.
     */
    @Override
    public void setRafterChoice(RafterChoice rafters)
    {
        this.rafters = rafters;
    }

    /**
     * Returns the shed included in the order. {@code null} means no shed is included.
     *
     * @return The shed included in the order.
     */
    @Override
    public Shed getShed()
    {
        return this.shed;
    }

    /**
     * Sets the shed included in the order. {@code null} means no shed is included.
     *
     * @param shed The new shed.
     */
    @Override
    public void setShed(Shed shed)
    {
        this.shed = shed;
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
    @Override
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @Override
    public boolean equals(Object o)
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
                Objects.equals(getCladding(), that.getCladding()) &&
                Objects.equals(getRoofing(), that.getRoofing()) &&
                getRafterChoice() == that.getRafterChoice() &&
                Objects.equals(getShed(), that.getShed()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
