package tvestergaard.fog.data.constraints;

/**
 * Represents a unary OR operator.
 */
public class UnaryOrCondition implements WhereCondition
{

    /**
     * The operand provided to the unary OR.
     */
    public final WhereCondition operand;

    /**
     * Creates a new {@link UnaryOrCondition}.
     *
     * @param operand The operand provided to the {@link UnaryOrCondition}.
     */
    public UnaryOrCondition(WhereCondition operand)
    {
        this.operand = operand;
    }

    /**
     * Returns the operand provided to the unary OR.
     *
     * @return The operand provided to the unary OR.
     */
    public WhereCondition getOperand()
    {
        return this.operand;
    }
}
