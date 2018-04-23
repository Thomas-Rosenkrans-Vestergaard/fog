package tvestergaard.fog.data.contraints;

/**
 * Represents the OR operator.
 */
public class BinaryOrCondition implements WhereCondition
{

    /**
     * The left hand operand.
     */
    public final WhereCondition left;

    /**
     * The right hand operand.
     */
    public final WhereCondition right;

    /**
     * Creates a new {@link BinaryOrCondition}.
     *
     * @param left  The left hand operand.
     * @param right The right hand operand.
     */
    public BinaryOrCondition(WhereCondition left, WhereCondition right)
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
     * Returns the right hand operand.
     *
     * @return The right hand operand.
     */
    public WhereCondition getRight()
    {
        return this.right;
    }
}
