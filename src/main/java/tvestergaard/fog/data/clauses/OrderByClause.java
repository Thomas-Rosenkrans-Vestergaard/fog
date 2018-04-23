package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an ORDER BY clause in SQL.
 */
public class OrderByClause implements Clause
{

    /**
     * The column to order by.
     */
    private final String column;

    /**
     * The direction to order in.
     */
    private final Direction direction;

    /**
     * Creates a new {@link OrderByClause} clause.
     *
     * @param column    The column to order by.
     * @param direction The direction to order in.
     */
    public OrderByClause(String column, Direction direction)
    {
        this.column = column;
        this.direction = direction;
    }

    /**
     * Represents a direction to order in.
     */
    public enum Direction
    {
        ASC,
        DESC
    }

    /**
     * Returns the order of the {@link Clause} in an SQL statement. The {@link Clause} with the smallest order comes first.
     *
     * @return The order of the {@link Clause} in an SQL statement.
     */
    @Override public int getOrder()
    {
        return 1;
    }

    /**
     * Appends the SQL of the clause to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the SQL onto.
     */
    @Override public void appendSQL(StringBuilder builder)
    {
        builder.append(' ');
        builder.append("ORDER BY `");
        builder.append(column);
        builder.append("` ");
        builder.append(direction);
    }

    /**
     * No implementation.
     */
    @Override
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex index) throws SQLException
    {

    }
}
