package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.roofing.Roofing;

public class ConstructionSpecification
{
    private final int     width;
    private final int     length;
    private final int     height;
    private final Roofing roofing;
    private final int     roofingSlope;

    public ConstructionSpecification(int width, int length, int height, Roofing roofing, int roofingSlope)
    {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofing = roofing;
        this.roofingSlope = roofingSlope;
    }

    public static ConstructionSpecification from(Order order)
    {
        return new ConstructionSpecification(order.getWidth(), order.getLength(), order.getHeight(), order.getRoofing(), order.getSlope());
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getLength()
    {
        return this.length;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Roofing getRoofing()
    {
        return this.roofing;
    }

    public int getRoofingSlope()
    {
        return this.roofingSlope;
    }
}
