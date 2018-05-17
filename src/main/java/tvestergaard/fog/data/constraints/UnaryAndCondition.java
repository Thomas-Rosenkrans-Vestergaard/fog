package tvestergaard.fog.data.constraints;

/**
 * Represents a unary 'AND' operator.
 */
public class UnaryAndCondition<C extends Column<C>> implements WhereCondition<C>
{

    /**
     * The operand provided to the {@link UnaryAndCondition}.
     */
    public final WhereCondition<C> operand;

    /**
     * Creates a new {@link UnaryAndCondition}.
     *
     * @param operand The operand provided to the {@link UnaryAndCondition}.
     */
    public UnaryAndCondition(WhereCondition<C> operand)
    {
        this.operand = operand;
    }

    /**
     * Returns the operand provided to the {@link UnaryAndCondition}.
     *
     * @return The operand provided to the {@link UnaryAndCondition}.
     */
    public WhereCondition<C> getOperand()
    {
        return this.operand;
    }
}