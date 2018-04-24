package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;

import java.time.LocalDateTime;

public interface Order
{

    /**
     * Returns the unique identifier of the {@link Order}.
     *
     * @return The unique identifier of the {@link Order}.
     */
    int getId();

    /**
     * Returns the {@link Customer} who placed the {@link Order}.
     *
     * @return The {@link Customer} who placed the {@link Order}.
     */
    Customer getCustomer();

    /**
     * Returns the {@link OrderType} of the {@link Order}.
     *
     * @return The {@link OrderType} of the {@link Order}.
     */
    OrderType getOrderType();

    /**
     * Sets the {@link OrderType} of the {@link Order}.
     *
     * @param orderType The new {@link OrderType}.
     */
    void setOrderType(OrderType orderType);

    /**
     * Returns the {@link Cladding} used on the {@link Order}.
     *
     * @return The {@link Cladding} used on the {@link Order}.
     */
    Cladding getCladding();

    /**
     * Sets the {@link Cladding} used on the {@link Order}.
     *
     * @param cladding The new {@link Cladding}.
     */
    void setCladding(Cladding cladding);

    /**
     * Returns the width of the {@link Order}.
     *
     * @return The width of the {@link Order}.
     */
    int getWidth();

    /**
     * Sets the width of the {@link Order}.
     *
     * @param width The new width.
     */
    void setWidth(int width);

    /**
     * Returns the length of the {@link Order}.
     *
     * @return The length of the {@link Order}.
     */
    int getLength();

    /**
     * Sets the length of the {@link Order}.
     *
     * @param length The new length.
     */
    void setLength(int length);

    /**
     * Returns the height of the {@link Order}.
     *
     * @return The height of the {@link Order}.
     */
    int getHeight();

    /**
     * Sets the height of the {@link Order}.
     *
     * @param height The new height.
     */
    void setHeight(int height);

    /**
     * Returns the {@link Roofing} used on the {@link Order}.
     *
     * @return The {@link Roofing} used on the {@link Order}.
     */
    Roofing getRoofing();

    /**
     * Sets the {@link Roofing} used on the {@link Order}.
     *
     * @param roofing The new {@link Roofing}.
     */
    void setRoofing(Roofing roofing);

    /**
     * Returns the slope of the {@link Roofing}.
     *
     * @return The slope of the {@link Roofing}.
     */
    int getSlope();

    /**
     * Sets the slope of the {@link Roofing}.
     *
     * @param slope The new slope.
     */
    void setSlope(int slope);

    /**
     * Returns the choice of rafters made by the {@link Customer} who placed the {@link Order}.
     *
     * @return The choice of rafters made by the {@link Customer} who placed the {@link Order}.
     */
    RaftersType getRaftersType();

    /**
     * Sets the {@link RaftersType} chosen by the {@link Customer} who placed the {@link Order}.
     *
     * @param type The new {@link RaftersType}.
     */
    void setRaftersType(RaftersType type);

    /**
     * Returns the {@link Shed} included in the {@link Order}. {@code null} means no {@link Shed} is included.
     *
     * @return The {@link Shed} included in the {@link Order}.
     */
    Shed getShed();

    /**
     * Sets the {@link Shed} included in the {@link Order}. {@code null} means no {@link Shed} is included.
     *
     * @param shed The new {@link Shed}.
     */
    void setShed(Shed shed);

    /**
     * Returns a {@link LocalDateTime} representing the moment in time when the {@link Customer} placed the {@link Order}.
     *
     * @return The {@link LocalDateTime}.
     */
    LocalDateTime getCreatedAt();

    /**
     * Checks that this {@link Order} equals another provided object. The two objects are only considered equal when
     * all the attributes of the two {@link Order}s are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the {@link Order}.
     *
     * @return The id of the {@link Order}.
     */
    int hashCode();

    /**
     * Represents a type of {@link Order}.
     */
    enum OrderType
    {

        /**
         * The {@link Customer} wants a shed.
         */
        SHED,

        /**
         * The {@link Customer} wants a garage.
         */
        GARAGE;

        public static OrderType from(int id)
        {
            if (id == 0)
                return GARAGE;
            if (id == 1)
                return SHED;

            throw new IllegalArgumentException("Unknown OrderType with provided id.");
        }
    }

    /**
     * Represents the rafters choice made by the {@link Customer}.
     */
    enum RaftersType
    {

        /**
         * The {@link Customer} receives rafters premade.
         */
        PREMADE(0),

        /**
         * The {@link Customer} makes the rafters themselves.
         */
        SELFMADE(1);

        /**
         * Identifier representing the {@link RaftersType}.
         */
        private final int id;

        /**
         * Creates a new {@link RaftersType}.
         *
         * @param id Identifier representing the {@link RaftersType}.
         */
        RaftersType(int id)
        {
            this.id = id;
        }

        /**
         * Returns the {@link RaftersType} with the provided id.
         *
         * @param id The identifier of the {@link RaftersType} to return.
         * @return The {@link RaftersType} with the provided id.
         */
        public static RaftersType from(int id)
        {
            if (id == 0)
                return PREMADE;
            if (id == 1)
                return SELFMADE;

            throw new IllegalArgumentException("Unknown RaftersType with provided id.");
        }
    }
}
