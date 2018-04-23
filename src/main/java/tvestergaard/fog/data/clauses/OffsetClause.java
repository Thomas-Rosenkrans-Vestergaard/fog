package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an OFFSET clause in SQL.
 */
public class OffsetClause implements Clause
{

    /**
     * The numerical offset.
     */
    private final int offset;

    /**
     * Creates a new {@link OffsetClause}.
     *
     * @param offset The numerical offset.
     */
    public OffsetClause(int offset)
    {
        this.offset = offset;
    }

    /**
     * Returns the order of the {@link Clause} in an SQL statement. The {@link Clause} with the smallest order comes first.
     *
     * @return The order of the {@link Clause} in an SQL statement.
     */
    @Override public int getOrder()
    {
        return 3;
    }

    /**
     * Appends the SQL of the clause to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the SQL onto.
     */
    @Override public void appendSQL(StringBuilder builder)
    {
        builder.append(" OFFSET ?");
    }

    /**
     * Sets the numerical offset as a parameter on the provided {@code PreparedStatement.}
     *
     * @param statement The statement to set the parameters on.
     * @param counter   Object containing the current parameter index to set.
     * @throws SQLException When a database exception occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex counter) throws SQLException
    {
        statement.setInt(counter.index++, offset);
    }
}
