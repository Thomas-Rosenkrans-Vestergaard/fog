package tvestergaard.fog.data.constraints;

public interface Constraint
{

    static <C extends Column<C>> Constraints<C> where(WhereCondition<C>... conditions)
    {
        Constraints<C> constraints = new Constraints<>();
        constraints.where(conditions);
        return constraints;
    }

    public static <C extends Column<C>> Constraints<C> groupBy(C... columns)
    {
        Constraints<C> constraints = new Constraints<C>();
        constraints.groupBy(columns);
        return constraints;
    }

    public static <C extends Column<C>> Constraints<C> having(WhereCondition<C>... conditions)
    {
        Constraints<C> constraints = new Constraints<C>();
        constraints.having(conditions);
        return constraints;
    }

    public static <C extends Column<C>> Constraints<C> order(C column, OrderDirection direction)
    {
        Constraints<C> constraints = new Constraints<>();
        constraints.order(column, direction);
        return constraints;
    }

    /**
     * Creates a new {@link EqualsCondition}.
     *
     * @param column The column used in the {@link EqualsCondition}.
     * @param value  The value to use in the {@link EqualsCondition}.
     * @return The newly created instance of {@link EqualsCondition}.
     */
    static <C extends Column<C>> EqualsCondition eq(C column, Object value)
    {
        return new EqualsCondition(column, value);
    }


    /**
     * Creates a new {@link NotCondition}.
     *
     * @param column The column used in the {@link NotCondition}.
     * @param value  The value to use in the {@link NotCondition}.
     * @return The newly created instance of {@link NotCondition}.
     */
    static <C extends Column<C>> NotCondition not(C column, Object value)
    {
        return new NotCondition(column, value);
    }

    /**
     * Creates a new {@link LikeCondition}.
     *
     * @param column The column used in the {@link LikeCondition}.
     * @param value  The value to use in the {@link LikeCondition}.
     * @return The newly created instance of {@link LikeCondition}.
     */
    static <C extends Column<C>> LikeCondition like(C column, String value)
    {
        return new LikeCondition(column, value);
    }

    /**
     * Creates a new {@link BinaryAndCondition} using the provided operands.
     *
     * @param left  The left operand.
     * @param right The right operand.
     * @return The newly created {@link BinaryAndCondition}.
     */
    static <C extends Column<C>> BinaryAndCondition and(WhereCondition<C> left, WhereCondition<C> right)
    {
        return new BinaryAndCondition(left, right);
    }

    /**
     * Creates a new {@link UnaryAndCondition}.
     *
     * @param operand The operand provided to the {@link UnaryAndCondition}.
     * @return The newly created instance of {@link UnaryAndCondition}.
     */
    static <C extends Column<C>> UnaryAndCondition and(WhereCondition<C> operand)
    {
        return new UnaryAndCondition(operand);
    }

    /**
     * Creates a new {@link BinaryOrCondition} using the provided operands.
     *
     * @param left  The left operand.
     * @param right The right operand.
     * @return The newly created {@link BinaryOrCondition}.
     */
    static <C extends Column<C>> BinaryOrCondition or(WhereCondition<C> left, WhereCondition<C> right)
    {
        return new BinaryOrCondition(left, right);
    }


    /**
     * Creates a new {@link UnaryOrCondition}.
     *
     * @param operand The operand provided to the {@link UnaryOrCondition}.
     * @return The newly created instance of {@link UnaryOrCondition}.
     */
    static <C extends Column<C>> UnaryOrCondition or(WhereCondition<C> operand)
    {
        return new UnaryOrCondition(operand);
    }
}
