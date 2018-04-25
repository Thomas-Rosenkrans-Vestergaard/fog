package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;

import java.time.LocalDateTime;
import java.util.Objects;

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
     * The {@link Type} of the {@link Order}.
     */
    private Type type;

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
    private Rafters raftersType;

    /**
     * The {@link Shed} included with the {@link Order}.
     */
    private Shed shed;

    /**
     * The moment when the {@link Order} was placed.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new {@link OrderRecord}.
     *
     * @param id          The unique identifier of the {@link Order}.
     * @param customer    The {@link Customer} who placed the {@link Order}.
     * @param type        The {@link Type} of the {@link Order}.
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
    public OrderRecord(int id, Customer customer, Type type, Cladding cladding, int width, int length, int height, Roofing roofing, int slope, Rafters raftersType, Shed shed, LocalDateTime createdAt)
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
     * Returns the {@link Type} of the {@link Order}.
     *
     * @return The {@link Type} of the {@link Order}.
     */
    @Override public Type getType()
    {
        return type;
    }

    /**
     * Sets the {@link Type} of the {@link Order}.
     *
     * @param type The new {@link Type}.
     */
    @Override public void setType(Type type)
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
        return cladding;
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
    @Override public Rafters getRafters()
    {
        return raftersType;
    }

    /**
     * Sets the {@link Rafters} chosen by the {@link Customer} who placed the {@link Order}.
     *
     * @param type The new {@link Rafters}.
     */
    @Override public void setRaftersConstruction(Rafters type)
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
     * Returns a {@code LocalDateTime} representing the moment in time when the {@link Customer} placed the {@link Order}.
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
                type == that.getType() &&
               Objects.equals(getCladding(), that.getCladding()) &&
               Objects.equals(getRoofing(), that.getRoofing()) &&
               getRafters() == that.getRafters() &&
               Objects.equals(getShed(), that.getShed()) &&
               Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
