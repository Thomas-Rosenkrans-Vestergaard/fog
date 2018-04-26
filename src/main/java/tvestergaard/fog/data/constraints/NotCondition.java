package tvestergaard.fog.data.constraints;

public class NotCondition<C extends Enum<C>> implements WhereCondition<C>
{

    /**
     * The column to use in the not comparison.
     */
    public final C column;

    /**
     * The value to use in the not comparison.
     */
    public final Object value;

    /**
     * Creates a new {@link NotCondition}.
     *
     * @param column The column to use in the not comparison.
     * @param value  The value to use in the not comparison.
     */
    public NotCondition(C column, Object value)
    {
        this.column = column;
        this.value = value;
    }

    /**
     * Returns the column to use in the not comparison.
     *
     * @return The column to use in the not comparison.
     */
    public C getColumn()
    {
        return this.column;
    }

    /**
     * Returns the value to use in the not comparison.
     *
     * @return The value to use in the not comparison.
     */
    public Object getValue()
    {
        return this.value;
    }
}
