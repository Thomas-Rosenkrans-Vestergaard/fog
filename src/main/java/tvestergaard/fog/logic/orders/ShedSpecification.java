package tvestergaard.fog.logic.orders;

public class ShedSpecification
{

    private final int depth;
    private final int claddingId;
    private final int flooringId;

    public ShedSpecification(int depth, int claddingId, int flooringId)
    {
        this.depth = depth;
        this.claddingId = claddingId;
        this.flooringId = flooringId;
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
