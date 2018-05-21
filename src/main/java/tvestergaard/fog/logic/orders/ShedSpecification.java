package tvestergaard.fog.logic.orders;

/**
 * Represents the specifications for some shed to be included in an order.
 */
public class ShedSpecification
{

    /**
     * The depth of the shed.
     */
    private final int depth;

    /**
     * The id of the cladding to be applied to the shed.
     */
    private final int claddingId;

    /**
     * The id of the flooring to be applied to the shed.
     */
    private final int flooringId;

    /**
     * Creates a new {@link ShedSpecification}.
     *
     * @param depth The depth of the shed.
     * @param claddingId The id of the cladding to be applied to the shed.
     * @param flooringId The id of the flooring to be applied to the shed.
     */
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
