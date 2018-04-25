package tvestergaard.fog.data.constraints;

/**
 * Offsets the records returned by some select operation.
 */
public class OffsetConstraint<C extends Enum<C>> implements Constraint<C>
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
