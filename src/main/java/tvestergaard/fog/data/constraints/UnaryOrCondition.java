package tvestergaard.fog.data.constraints;

/**
 * Represents a unary OR operator.
 */
public class UnaryOrCondition<C extends Column<C>> implements WhereCondition<C>
{

    /**
     * The operand provided to the unary OR.
     */
    public final WhereCondition<C> operand;

    /**
     * Creates a new {@link UnaryOrCondition}.
     *
     * @param operand The operand provided to the {@link UnaryOrCondition}.
     */
    public UnaryOrCondition(WhereCondition<C> operand)
    {
        this.operand = operand;
    }

    /**
     * Returns the operand provided to the unary OR.
     *
     * @return The operand provided to the unary OR.
     */
    public WhereCondition<C> getOperand()
    {
        return this.operand;
    }
}
