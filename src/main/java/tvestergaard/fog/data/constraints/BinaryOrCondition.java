package tvestergaard.fog.data.constraints;

/**
 * Represents the OR operator.
 */
public class BinaryOrCondition<C extends Column<C>> implements WhereCondition<C>
{

    /**
     * The left hand operand.
     */
    public final WhereCondition<C> left;

    /**
     * The right hand operand.
     */
    public final WhereCondition<C> right;

    /**
     * Creates a new {@link BinaryOrCondition}.
     *
     * @param left  The left hand operand.
     * @param right The right hand operand.
     */
    public BinaryOrCondition(WhereCondition<C> left, WhereCondition<C> right)
    {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left hand operand.
     *
     * @return The left hand operand.
     */
    public WhereCondition<C> getLeft()
    {
        return this.left;
    }

    /**
     * Returns the right hand operand.
     *
     * @return The right hand operand.
     */
    public WhereCondition<C> getRight()
    {
        return this.right;
    }
}
