package tvestergaard.fog.data.constraints;

/**
 * Represents and AND operator.
 */
public class BinaryAndCondition<C extends Enum<C>> implements WhereCondition<C>
{

    /**
     * The left hand operand.
     */
    public final WhereCondition<C> left;

    /**
     * The right hash operand.
     */
    public final WhereCondition<C> right;

    /**
     * Creates a new {@link BinaryAndCondition}.
     *
     * @param left  The left hand operand.
     * @param right The right hand operand.
     */
    public BinaryAndCondition(WhereCondition<C> left, WhereCondition<C> right)
    {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left hand operand.
     *
     * @return The left hand operand.
     */
    public WhereCondition getLeft()
    {
        return this.left;
    }

    /**
     * Returns the right hash operand.
     *
     * @return The right hash operand.
     */
    public WhereCondition getRight()
    {
        return this.right;
    }
}