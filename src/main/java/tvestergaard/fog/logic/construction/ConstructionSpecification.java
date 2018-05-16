package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.Shed;
import tvestergaard.fog.data.roofing.Roofing;

public class ConstructionSpecification
{

    /**
     * The width of the garage in centimeters.
     */
    private final int width;

    /**
     * The length of the garage in centimeters.
     */
    private final int length;

    /**
     * The height of the garage in centimeters.
     */
    private final int height;

    /**
     * The roofing used on the garage.
     */
    private final Roofing roofing;

    /**
     * The slope of the roofing.
     */
    private final int roofingSlope;

    /**
     * The specifications of the shed in the garage.
     */
    private final Shed shed;

    /**
     * Creates a new {@link ConstructionSpecification}.
     *
     * @param width        The width of the garage in centimeters.
     * @param length       The length of the garage in centimeters.
     * @param height       The height of the garage in centimeters.
     * @param roofing      The roofing used on the garage.
     * @param roofingSlope The slope of the roofing.
     * @param shed         The specifications of the shed in the garage.
     */
    public ConstructionSpecification(int width, int length, int height, Roofing roofing, int roofingSlope, Shed shed)
    {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofing = roofing;
        this.roofingSlope = roofingSlope;
        this.shed = shed;
    }

    /**
     * Creates a new {@link ConstructionSpecification} from the provided order
     *
     * @param order The order from which to create the {@link ConstructionSpecification}.
     * @return The newly created {@link ConstructionSpecification}.
     */
    public static ConstructionSpecification from(Order order)
    {
        return new ConstructionSpecification(order.getWidth(), order.getLength(), order.getHeight(), order.getRoofing(), order.getSlope(), order.getShed());
    }

    /**
     * Returns the width of the garage in centimeters.
     *
     * @return The width of the garage in centimeters.
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * Returns the length of the garage in centimeters.
     *
     * @return The length of the garage in centimeters.
     */
    public int getLength()
    {
        return this.length;
    }

    /**
     * Returns the height of the garage in centimeters.
     *
     * @return The height of the garage in centimeters.
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * Returns the roofing used on the garage.
     *
     * @return The roofing used on the garage.
     */
    public Roofing getRoofing()
    {
        return this.roofing;
    }

    /**
     * Returns the slope of the roofing.
     *
     * @return The slope of the roofing.
     */
    public int getRoofingSlope()
    {
        return this.roofingSlope;
    }

    /**
     * Returns the specifications of the shed in the garage.
     *
     * @return The specifications of the shed in the garage.
     */
    public Shed getShed()
    {
        return this.shed;
    }
}
