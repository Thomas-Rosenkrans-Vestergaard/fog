package tvestergaard.fog.data.constraints;

import tvestergaard.fog.data.constraints.OrderConstraint.Direction;

public interface Constraint
{

    /**
     * Appends the provided element to the provided array.
     *
     * @param array   The array to append the element to.
     * @param element The element to append to the array.
     * @return The extended array.
     */
    static Constraint[] append(Constraint[] array, Constraint element)
    {
        Constraint[] newArray = new Constraint[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = element;
        return newArray;
    }

    /**
     * Creates a new {@link LimitConstraint} using the provided numerical limit.
     *
     * @param limit The numerical limit.
     * @return The newly created {@link LimitConstraint}.
     */
    static LimitConstraint limit(int limit)
    {
        return new LimitConstraint(limit);
    }

    /**
     * Creates a new {@link OffsetConstraint} using the provided numerical offset..
     *
     * @param offset The numerical offset.
     * @return The newly created {@link OffsetConstraint}.
     */
    static OffsetConstraint offset(int offset)
    {
        return new OffsetConstraint(offset);
    }

    /**
     * Creates a new {@link OrderConstraint}.
     *
     * @param column    The column to order by.
     * @param direction The direction to order in.
     * @return The newly created {@link OrderConstraint}.
     */
    static OrderConstraint order(String column, Direction direction)
    {
        return new OrderConstraint(column, direction);
    }

    /**
     * Creates a new {@link WhereConstraint}.
     *
     * @param conditions The {@link WhereCondition}s to use in the {@link WhereConstraint}.
     * @return The newly created {@link WhereConstraint}.
     */
    static WhereConstraint where(WhereCondition... conditions)
    {
        return new WhereConstraint(conditions);
    }

    /**
     * Creates a new {@link EqualsCondition}.
     *
     * @param column The name of the column used in the {@link EqualsCondition}.
     * @param value  The value to use in the {@link EqualsCondition}.
     * @return The newly created instance of {@link EqualsCondition}.
     */
    static EqualsCondition eq(String column, Object value)
    {
        return new EqualsCondition(column, value);
    }


    /**
     * Creates a new {@link LikeCondition}.
     *
     * @param column The name of the column used in the {@link LikeCondition}.
     * @param value  The value to use in the {@link LikeCondition}.
     * @return The newly created instance of {@link LikeCondition}.
     */
    static LikeCondition like(String column, String value)
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
    static BinaryAndCondition and(WhereCondition left, WhereCondition right)
    {
        return new BinaryAndCondition(left, right);
    }

    /**
     * Creates a new {@link UnaryAndCondition}.
     *
     * @param operand The operand provided to the {@link UnaryAndCondition}.
     * @return The newly created instance of {@link UnaryAndCondition}.
     */
    static UnaryAndCondition and(WhereCondition operand)
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
    static BinaryOrCondition or(WhereCondition left, WhereCondition right)
    {
        return new BinaryOrCondition(left, right);
    }


    /**
     * Creates a new {@link UnaryOrCondition}.
     *
     * @param operand The operand provided to the {@link UnaryOrCondition}.
     * @return The newly created instance of {@link UnaryOrCondition}.
     */
    static UnaryOrCondition or(WhereCondition operand)
    {
        return new UnaryOrCondition(operand);
    }

    /**
     * Returns {@link Direction#ASC}.
     *
     * @return {@link Direction#ASC}.
     */
    static Direction asc()
    {
        return Direction.ASC;
    }

    /**
     * Returns {@link Direction#DESC}.
     *
     * @return {@link Direction#DESC}.
     */
    static Direction desc()
    {
        return Direction.DESC;
    }
}
