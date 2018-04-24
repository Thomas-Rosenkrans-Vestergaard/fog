package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;

import java.sql.Timestamp;

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
     * @return The chaice of rafters made by the {@link Customer} who placed the {@link Order}.
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
     * Returns a timestamp representing the moment in time when the {@link Customer} placed the {@link Order}.
     *
     * @return The timestamp.
     */
    Timestamp getCreatedAt();

    /**
     * Represents a type of {@link Order}.
     */
    public enum OrderType
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
    public enum RaftersType
    {

        /**
         * The {@link Customer} receives rafters premade.
         */
        PREMADE,

        /**
         * The {@link Customer} makes the rafters themselves.
         */
        SELFMADE;

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
