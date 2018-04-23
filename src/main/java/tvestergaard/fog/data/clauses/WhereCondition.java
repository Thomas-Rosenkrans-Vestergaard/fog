package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents a condition in a {@link WhereClause}.
 */
public interface WhereCondition
{

    /**
     * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the string representation onto.
     */
    public void appendSQL(StringBuilder builder);

    /**
     * Sets the parameters needed by the {@link WhereCondition}.
     *
     * @param statement      The statement to set the parameters upon.
     * @param parameterIndex The object containing the current index to set.
     * @throws SQLException When a database exception occurs.
     */
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException;
}
