package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;

import java.sql.Timestamp;

/**
 * The default {@link Order} implementation.
 */
public class OrderRecord implements Order
{

    /**
     * The unique identifier of the {@link Order}.
     */
    private final int id;

    /**
     * The {@link Customer} who placed the {@link Order}.
     */
    private Customer customer;

    /**
     * The {@link OrderType} of the {@link Order}.
     */
    private OrderType type;

    /**
     * The {@link Cladding} used on the {@link Order}.
     */
    private Cladding cladding;

    /**
     * The width of the {@link Order}.
     */
    private int width;

    /**
     * The length of the {@link Order}.
     */
    private int length;

    /**
     * The height of the {@link Order}.
     */
    private int height;

    /**
     * The {@link Roofing} used on the {@link Order}.
     */
    private Roofing roofing;

    /**
     * The slope of the {@link Roofing}.
     */
    private int slope;

    /**
     * The type of rafters chosen by the {@link Customer} who placed the {@link Order}.
     */
    private RaftersType raftersType;

    /**
     * The {@link Shed} included with the {@link Order}.
     */
    private Shed shed;

    /**
     * The time when the {@link Order} was placed.
     */
    private Timestamp createdAt;

    /**
     * Creates a new {@link OrderRecord}.
     *
     * @param id          The unique identifier of the {@link Order}.
     * @param customer    The {@link Customer} who placed the {@link Order}.
     * @param type        The {@link OrderType} of the {@link Order}.
     * @param cladding    The {@link Cladding} used on the {@link Order}.
     * @param width       The width of the {@link Order}.
     * @param length      The length of the {@link Order}.
     * @param height      The height of the {@link Order}.
     * @param roofing     The {@link Roofing} used on the {@link Order}.
     * @param slope       The slope of the {@link Roofing}.
     * @param raftersType The type of rafters chosen by the {@link Customer} who placed the {@link Order}.
     * @param shed        The {@link Shed} included with the {@link Order}.
     * @param createdAt   The time when the {@link Order} was placed.
     */
    public OrderRecord(int id, Customer customer, OrderType type, Cladding cladding, int width, int length, int height, Roofing roofing, int slope, RaftersType raftersType, Shed shed, Timestamp createdAt)
    {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.cladding = cladding;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofing = roofing;
        this.slope = slope;
        this.raftersType = raftersType;
        this.shed = shed;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the {@link Order}.
     *
     * @return The unique identifier of the {@link Order}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the {@link Customer} who placed the {@link Order}.
     *
     * @return The {@link Customer} who placed the {@link Order}.
     */
    @Override public Customer getCustomer()
    {
        return customer;
    }

    /**
     * Returns the {@link OrderType} of the {@link Order}.
     *
     * @return The {@link OrderType} of the {@link Order}.
     */
    @Override public OrderType getOrderType()
    {
        return type;
    }

    /**
     * Sets the {@link OrderType} of the {@link Order}.
     *
     * @param orderType The new {@link OrderType}.
     */
    @Override public void setOrderType(OrderType orderType)
    {
        this.type = type;
    }

    /**
     * Returns the {@link Cladding} used on the {@link Order}.
     *
     * @return The {@link Cladding} used on the {@link Order}.
     */
    @Override public Cladding getCladding()
    {
        return null;
    }

    /**
     * Sets the {@link Cladding} used on the {@link Order}.
     *
     * @param cladding The new {@link Cladding}.
     */
    @Override public void setCladding(Cladding cladding)
    {
        this.cladding = cladding;
    }

    /**
     * Returns the width of the {@link Order}.
     *
     * @return The width of the {@link Order}.
     */
    @Override public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the {@link Order}.
     *
     * @param width The new width.
     */
    @Override public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the length of the {@link Order}.
     *
     * @return The length of the {@link Order}.
     */
    @Override public int getLength()
    {
        return length;
    }

    /**
     * Sets the length of the {@link Order}.
     *
     * @param length The new length.
     */
    @Override public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * Returns the height of the {@link Order}.
     *
     * @return The height of the {@link Order}.
     */
    @Override public int getHeight()
    {
        return height;
    }

    /**
     * Sets the height of the {@link Order}.
     *
     * @param height The new height.
     */
    @Override public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Returns the {@link Roofing} used on the {@link Order}.
     *
     * @return The {@link Roofing} used on the {@link Order}.
     */
    @Override public Roofing getRoofing()
    {
        return roofing;
    }

    /**
     * Sets the {@link Roofing} used on the {@link Order}.
     *
     * @param roofing The new {@link Roofing}.
     */
    @Override public void setRoofing(Roofing roofing)
    {
        this.roofing = roofing;
    }

    /**
     * Returns the slope of the {@link Roofing}.
     *
     * @return The slope of the {@link Roofing}.
     */
    @Override public int getSlope()
    {
        return slope;
    }

    /**
     * Sets the slope of the {@link Roofing}.
     *
     * @param slope The new slope.
     */
    @Override public void setSlope(int slope)
    {
        this.slope = slope;
    }

    /**
     * Returns the choice of rafters made by the {@link Customer} who placed the {@link Order}.
     *
     * @return The chaice of rafters made by the {@link Customer} who placed the {@link Order}.
     */
    @Override public RaftersType getRaftersType()
    {
        return raftersType;
    }

    /**
     * Sets the {@link RaftersType} chosen by the {@link Customer} who placed the {@link Order}.
     *
     * @param type The new {@link RaftersType}.
     */
    @Override public void setRaftersType(RaftersType type)
    {
        this.raftersType = raftersType;
    }

    /**
     * Returns the {@link Shed} included in the {@link Order}. {@code null} means no {@link Shed} is included.
     *
     * @return The {@link Shed} included in the {@link Order}.
     */
    @Override public Shed getShed()
    {
        return this.shed;
    }

    /**
     * Sets the {@link Shed} included in the {@link Order}. {@code null} means no {@link Shed} is included.
     *
     * @param shed The new {@link Shed}.
     */
    @Override public void setShed(Shed shed)
    {
        this.shed = shed;
    }

    /**
     * Returns a timestamp representing the moment in time when the {@link Customer} placed the {@link Order}.
     *
     * @return The timestamp.
     */
    @Override public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRecord that = (OrderRecord) o;

        return id == that.id;
    }

    @Override public int hashCode()
    {
        return id;
    }
}
