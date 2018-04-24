package tvestergaard.fog.data.constraints;

/**
 * Represents the LIKE operator from SQL.
 */
public class LikeCondition implements WhereCondition
{

    /**
     * The column to perform the like comparison upon.
     */
    public final String column;

    /**
     * The operand provided to the like condition.
     */
    public final String operand;

    /**
     * Creates a new {@link LikeCondition}.
     *
     * @param column  The column to perform the like comparison upon.
     * @param operand The operand provided to the like condition.
     */
    public LikeCondition(String column, String operand)
    {
        this.column = column;
        this.operand = operand;
    }

    /**
     * Returns the column to perform the like comparison upon.
     *
     * @return The column to perform the like comparison upon.
     */
    public String getColumn()
    {
        return this.column;
    }

    /**
     * Returns the operand provided to the like condition.
     *
     * @return The operand provided to the like condition.
     */
    public String getOperand()
    {
        return this.operand;
    }
}
