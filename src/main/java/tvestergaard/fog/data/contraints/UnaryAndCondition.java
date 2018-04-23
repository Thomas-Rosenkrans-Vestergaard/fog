package tvestergaard.fog.data.contraints;

/**
 * Represents a unary 'AND' operator.
 */
public class UnaryAndCondition implements WhereCondition
{

    /**
     * The operand provided to the {@link UnaryAndCondition}.
     */
    public final WhereCondition operand;

    /**
     * Creates a new {@link UnaryAndCondition}.
     *
     * @param operand The operand provided to the {@link UnaryAndCondition}.
     */
    public UnaryAndCondition(WhereCondition operand)
    {
        this.operand = operand;
    }

    /**
     * Returns the operand provided to the {@link UnaryAndCondition}.
     *
     * @return The operand provided to the {@link UnaryAndCondition}.
     */
    public WhereCondition getOperand()
    {
        return this.operand;
    }
}