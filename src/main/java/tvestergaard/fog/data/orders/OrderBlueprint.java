package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;

public interface OrderBlueprint
{

    /**
     * Returns the customer who placed the order.
     *
     * @return The customer who placed the order.
     */
    Customer getCustomer();

    /**
     * Returns the cladding used on the order.
     *
     * @return The cladding used on the order.
     */
    Cladding getCladding();

    /**
     * Sets the cladding used on the order.
     *
     * @param cladding The new cladding.
     */
    void setCladding(Cladding cladding);

    /**
     * Returns the width of the order.
     *
     * @return The width of the order.
     */
    int getWidth();

    /**
     * Sets the width of the order.
     *
     * @param width The new width.
     */
    void setWidth(int width);

    /**
     * Returns the length of the order.
     *
     * @return The length of the order.
     */
    int getLength();

    /**
     * Sets the length of the order.
     *
     * @param length The new length.
     */
    void setLength(int length);

    /**
     * Returns the height of the order.
     *
     * @return The height of the order.
     */
    int getHeight();

    /**
     * Sets the height of the order.
     *
     * @param height The new height.
     */
    void setHeight(int height);

    /**
     * Returns the roofing used on the order.
     *
     * @return The roofing used on the order.
     */
    Roofing getRoofing();

    /**
     * Sets the roofing used on the order.
     *
     * @param roofing The new roofing.
     */
    void setRoofing(Roofing roofing);

    /**
     * Returns the slope of the roofing.
     *
     * @return The slope of the roofing.
     */
    int getSlope();

    /**
     * Sets the slope of the roofing.
     *
     * @param slope The new slope.
     */
    void setSlope(int slope);

    /**
     * Returns the choice of rafters made by the customer who placed the order.
     *
     * @return The choice of rafters made by the customer who placed the order.
     */
    RafterChoice getRafterChoice();

    /**
     * Sets the rafters chosen by the customer who placed the order.
     *
     * @param rafters The new rafters.
     */
    void setRafterChoice(RafterChoice rafters);

    /**
     * Returns the shed included in the order. {@code null} means no shed is included.
     *
     * @return The shed included in the order.
     */
    ShedBlueprint getShed();

    /**
     * Sets the shed included in the order. {@code null} means no shed is included.
     *
     * @param shed The new shed.
     */
    void setShed(Shed shed);
}
