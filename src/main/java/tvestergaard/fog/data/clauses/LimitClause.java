package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents a LIMIT clause in SQL.
 */
public class LimitClause implements Clause
{

    /**
     * The numerical limit.
     */
    private final int limit;

    /**
     * Creates a new {@link LimitClause}. Use the {@link Clause#limit(int)} method to create new instances in main code.
     *
     * @param limit The numerical limit.
     */
    public LimitClause(int limit)
    {
        this.limit = limit;
    }

    /**
     * Returns the numerical limit.
     *
     * @return The numerical limit.
     */
    public int getLimit()
    {
        return this.limit;
    }

    /**
     * Returns the order of the {@link Clause} in an SQL statement. The {@link Clause} with the smallest order comes first.
     *
     * @return The order of the {@link Clause} in an SQL statement.
     */
    @Override public int getOrder()
    {
        return 2;
    }

    /**
     * Appends the SQL of the clause to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the SQL onto.
     */
    @Override public void appendSQL(StringBuilder builder)
    {
        builder.append(" LIMIT ?");
    }

    /**
     * Sets the numerical limit as a parameter on the provided {@code PreparedStatement.}
     *
     * @param statement The statement to set the parameters on.
     * @param counter   Object containing the current parameter index to set.
     * @throws SQLException When a database exception occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex counter) throws SQLException
    {
        statement.setInt(counter.index++, limit);
    }
}
