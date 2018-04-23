package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Clause
{
    /**
     * Appends the SQL of the clause to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the SQL onto.
     */
    public void appendSQL(StringBuilder builder);

    /**
     * Sets the parameters needed by the {@link Clause}.
     *
     * @param statement The statement to set the parameters on.
     * @param index     Object containing the current parameter index to set.
     * @throws SQLException When a database exception occurs.
     */
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex index) throws SQLException;

    /**
     * Returns the order of the {@link Clause} in an SQL statement. The {@link Clause} with the smallest order comes first.
     *
     * @return The order of the {@link Clause} in an SQL statement.
     */
    public int getOrder();

    /**
     * Creates a new {@link LimitClause} using the provided numerical limit.
     *
     * @param limit The numerical limit.
     * @return The newly created {@link LimitClause}.
     */
    public static LimitClause limit(int limit)
    {
        return new LimitClause(limit);
    }

    /**
     * Creates a new {@link OffsetClause} using the provided numerical offset..
     *
     * @param offset The numerical offset.
     * @return The newly created {@link OffsetClause}.
     */
    public static OffsetClause offset(int offset)
    {
        return new OffsetClause(offset);
    }

    /**
     * Creates a new {@link OrderByClause}.
     *
     * @param column    The column to order by.
     * @param direction The direction to order in.
     * @return The newly created {@link OrderByClause}.
     */
    public static OrderByClause orderBy(String column, OrderByClause.Direction direction)
    {
        return new OrderByClause(column, direction);
    }

    /**
     * Returns {@link tvestergaard.fog.data.clauses.OrderByClause.Direction#ASC}.
     *
     * @return {@link tvestergaard.fog.data.clauses.OrderByClause.Direction#ASC}.
     */
    public static OrderByClause.Direction asc()
    {
        return OrderByClause.Direction.ASC;
    }

    /**
     * Returns {@link tvestergaard.fog.data.clauses.OrderByClause.Direction#DESC}.
     *
     * @return {@link tvestergaard.fog.data.clauses.OrderByClause.Direction#DESC}.
     */
    public static OrderByClause.Direction desc()
    {
        return OrderByClause.Direction.DESC;
    }

    /**
     * Creates a new {@link WhereClause}.
     *
     * @param conditions The {@link WhereCondition}s to use in the {@link WhereClause}.
     * @return The newly created {@link WhereClause}.
     */
    public static WhereClause where(WhereCondition... conditions)
    {
        return new WhereClause(conditions);
    }

    /**
     * Creates a new {@link tvestergaard.fog.data.clauses.WhereClause.EqualsCondition}.
     *
     * @param column The name of the column used in the {@link tvestergaard.fog.data.clauses.WhereClause.EqualsCondition}.
     * @param value  The value to use in the {@link tvestergaard.fog.data.clauses.WhereClause.EqualsCondition}.
     * @return The newly created instance of {@link tvestergaard.fog.data.clauses.WhereClause.EqualsCondition}.
     */
    public static WhereCondition eq(String column, Object value)
    {
        return new WhereClause.EqualsCondition(column, value);
    }


    /**
     * Creates a new {@link tvestergaard.fog.data.clauses.WhereClause.LikeCondition}.
     *
     * @param column The name of the column used in the {@link tvestergaard.fog.data.clauses.WhereClause.LikeCondition}.
     * @param value  The value to use in the {@link tvestergaard.fog.data.clauses.WhereClause.LikeCondition}.
     * @return The newly created instance of {@link tvestergaard.fog.data.clauses.WhereClause.LikeCondition}.
     */
    public static WhereCondition like(String column, String value)
    {
        return new WhereClause.LikeCondition(column, value);
    }

    /**
     * Creates a new {@link tvestergaard.fog.data.clauses.WhereClause.AndCondition} using the provided operands.
     *
     * @param left  The left operand.
     * @param right The right operand.
     * @return The newly created {@link tvestergaard.fog.data.clauses.WhereClause.AndCondition}.
     */
    public static WhereCondition and(WhereCondition left, WhereCondition right)
    {
        return new WhereClause.AndCondition(left, right);
    }

    /**
     * Creates a new {@link tvestergaard.fog.data.clauses.WhereClause.OrCondition} using the provided operands.
     *
     * @param left  The left operand.
     * @param right The right operand.
     * @return The newly created {@link tvestergaard.fog.data.clauses.WhereClause.OrCondition}.
     */
    public static WhereCondition or(WhereCondition left, WhereCondition right)
    {
        return new WhereClause.OrCondition(left, right);
    }
}
