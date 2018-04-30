package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.orders.ShedBlueprint;

public class ShedSpecification
{

    private final int width;
    private final int depth;
    private final int claddingId;
    private final int flooringId;
    public ShedSpecification(int width, int depth, int claddingId, int flooringId)
    {
        this.width = width;
        this.depth = depth;
        this.claddingId = claddingId;
        this.flooringId = flooringId;
    }

    /**
     * Returns the width of the shed.
     *
     * @return The width of the shed.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the depth of the shed.
     *
     * @return The depth of the shed.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Returns the id of the cladding used on the shed.
     *
     * @return The id of the cladding used on the shed.
     */
    public int getCladdingId()
    {
        return claddingId;
    }

    /**
     * Returns the id of the flooring used on the shed.
     *
     * @return The id of the flooring used on the shed.
     */
    public int getFlooringId()
    {
        return flooringId;
    }
}
