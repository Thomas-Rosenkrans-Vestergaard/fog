package tvestergaard.fog.data.constraints;

/**
 * Represents the equals (=) operator.
 */
public class EqualsCondition<C extends Enum<C>> implements WhereCondition<C>
{

    /**
     * The column to use in the equals comparison.
     */
    public final C column;

    /**
     * The value to use in the equals comparison.
     */
    public final Object value;

    /**
     * Creates a new {@link EqualsCondition}.
     *
     * @param column The column to use in the equals comparison.
     * @param value  The value to use in the equals comparison.
     */
    public EqualsCondition(C column, Object value)
    {
        this.column = column;
        this.value = value;
    }

    /**
     * Returns the column to use in the equals comparison.
     *
     * @return The column to use in the equals comparison.
     */
    public C getColumn()
    {
        return this.column;
    }

    /**
     * Returns the value to use in the equals comparison.
     *
     * @return The value to use in the equals comparison.
     */
    public Object getValue()
    {
        return this.value;
    }
}