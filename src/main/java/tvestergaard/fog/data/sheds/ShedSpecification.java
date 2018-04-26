package tvestergaard.fog.data.sheds;

public class ShedSpecification
{
    /**
     * The width of the shed specifications.
     */
    private final int width;

    /**
     * The depth of the shed specifications.
     */
    private final int depth;

    /**
     * The cladding used on the shed specifications.
     */
    private final int cladding;

    /**
     * The flooring used on the shed specifications.
     */
    private final int flooring;

    /**
     * Creates a new {@link ShedSpecification}.
     *
     * @param width    The width of the shed specifications.
     * @param depth    The depth of the shed specifications.
     * @param cladding The cladding used on the shed specifications.
     * @param flooring The flooring used on the shed specifications.
     */
    public ShedSpecification(int width, int depth, int cladding, int flooring)
    {
        this.width = width;
        this.depth = depth;
        this.cladding = cladding;
        this.flooring = flooring;
    }

    /**
     * Creates a new {@link ShedSpecification} from the provided {@link Shed}.
     *
     * @param shed The shed to use when creating the shed specification.
     */
    public ShedSpecification(Shed shed)
    {
        this(shed.getWidth(), shed.getDepth(), shed.getCladding().getId(), shed.getFlooring().getId());
    }

    /**
     * Returns the width of the shed specifications.
     *
     * @return The width of the shed specifications.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the depth of the shed specifications.
     *
     * @return The depth of the shed specifications.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Returns the cladding used on the shed specifications.
     *
     * @return The cladding used on the shed specifications.
     */
    public int getCladding()
    {
        return cladding;
    }

    /**
     * Returns the flooring used on the shed specifications.
     *
     * @return The flooring used on the shed specifications.
     */
    public int getFlooring()
    {
        return flooring;
    }
}
