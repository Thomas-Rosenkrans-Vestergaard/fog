package tvestergaard.fog.data.contraints;

/**
 * Offsets the records returned by some select operation.
 */
public class OffsetConstraint implements Constraint
{

    /**
     * The offset of the records returned by the select operation.
     */
    public final int offset;

    /**
     * Creates a new {@link OffsetConstraint}.
     *
     * @param offset The offset of the records returned by the select operation.
     */
    public OffsetConstraint(int offset)
    {
        this.offset = offset;
    }

    /**
     * Returns the offset of the records returned by the select operation.
     *
     * @return The offset of the records returned by the select operation.
     */
    public int getOffset()
    {
        return this.offset;
    }
}
