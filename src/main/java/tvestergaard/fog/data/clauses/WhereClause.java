package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents a WHERE clause in SQL.
 */
public class WhereClause implements Clause
{

    /**
     * The conditions defined in the {@link WhereClause}.
     */
    private final WhereCondition[] conditions;

    /**
     * Creates a new {@link WhereClause}.
     *
     * @param conditions The conditions defined in the {@link WhereClause}.
     */
    public WhereClause(WhereCondition... conditions)
    {
        this.conditions = conditions;
    }

    /**
     * Returns the order of the {@link Clause} in an SQL statement. The {@link Clause} with the smallest order comes first.
     *
     * @return The order of the {@link Clause} in an SQL statement.
     */
    @Override public int getOrder()
    {
        return 0;
    }

    /**
     * Appends the SQL of the clause to the provided {@code StringBuilder}.
     *
     * @param builder The {@code StringBuilder} to append the SQL onto.
     */
    @Override public void appendSQL(StringBuilder builder)
    {
        builder.append(' ');
        builder.append("WHERE");
        for (WhereCondition condition : conditions)
            condition.appendSQL(builder);
    }

    /**
     * Sets the parameters needed by the {@link Clause}.
     *
     * @param statement The statement to set the parameters on.
     * @param index     Object containing the current parameter index to set.
     * @throws SQLException When a database exception occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement, Generator.ParameterIndex index) throws SQLException
    {
        for (WhereCondition condition : conditions)
            condition.setParameters(statement, index);
    }

    /**
     * Represents the AND operator in SQL.
     */
    public static class BinaryAndCondition implements WhereCondition
    {

        /**
         * The left hand operand.
         */
        private final WhereCondition left;

        /**
         * The right hash operand.
         */
        private final WhereCondition right;

        /**
         * Creates a new {@link BinaryAndCondition}.
         *
         * @param left  The left hand operand.
         * @param right The right hand operand.
         */
        public BinaryAndCondition(WhereCondition left, WhereCondition right)
        {
            this.left = left;
            this.right = right;
        }

        /**
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(' ');
            builder.append('(');
            left.appendSQL(builder);
            builder.append(" AND ");
            right.appendSQL(builder);
            builder.append(')');
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            left.setParameters(statement, parameterIndex);
            right.setParameters(statement, parameterIndex);
        }
    }

    /**
     * Represents the OR operator in SQL.
     */
    public static class BinaryOrCondition implements WhereCondition
    {

        /**
         * The left hand operator.
         */
        private final WhereCondition left;

        /**
         * The right hand operator.
         */
        private final WhereCondition right;

        /**
         * Creates a new {@link BinaryOrCondition}.
         *
         * @param left  The left hand operator.
         * @param right The right hand operator.
         */
        public BinaryOrCondition(WhereCondition left, WhereCondition right)
        {
            this.left = left;
            this.right = right;
        }

        /**
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(' ');
            builder.append('(');
            left.appendSQL(builder);
            builder.append(" OR ");
            right.appendSQL(builder);
            builder.append(')');
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            left.setParameters(statement, parameterIndex);
            right.setParameters(statement, parameterIndex);
        }
    }

    /**
     * Represents a unary AND operator in SQL of the form OR {operand}.
     */
    public static class UnaryAndCondition implements WhereCondition
    {

        /**
         * The operand.
         */
        private final WhereCondition operand;

        /**
         * Creates a new {@link UnaryAndCondition}.
         *
         * @param operand The operand provided to the {@link UnaryAndCondition}.
         */
        public UnaryAndCondition(WhereCondition operand)
        {
            this.operand = operand;
        }

        /**
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(" AND ?");
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            statement.setObject(parameterIndex.index++, operand);
        }
    }


    /**
     * Represents a unary OR operator in SQL of the form OR {operand}.
     */
    public static class UnaryOrCondition implements WhereCondition
    {

        /**
         * The operand.
         */
        private final WhereCondition operand;

        /**
         * Creates a new {@link UnaryOrCondition}.
         *
         * @param operand The operand provided to the {@link UnaryOrCondition}.
         */
        public UnaryOrCondition(WhereCondition operand)
        {
            this.operand = operand;
        }

        /**
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(" OR ?");
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            statement.setObject(parameterIndex.index++, operand);
        }
    }

    /**
     * Represents the equals operator in SQL.
     */
    public static class EqualsCondition implements WhereCondition
    {

        /**
         * The column to use in the equals comparison.
         */
        private final String column;

        /**
         * The value to use in the equals comparison.
         */
        private final Object value;

        /**
         * Creates a new {@link EqualsCondition}.
         *
         * @param column The column to use in the equals comparison.
         * @param value  The value to use in the equals comparison.
         */
        public EqualsCondition(String column, Object value)
        {
            this.column = column;
            this.value = value;
        }

        /**
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(String.format(" `%s` = ?", column));
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            statement.setObject(parameterIndex.index++, value);
        }
    }

    /**
     * Represents a LIKE condition in SQL.
     */
    public static class LikeCondition implements WhereCondition
    {

        /**
         * The column to perform the like comparison upon.
         */
        private final String column;

        /**
         * The operand provided to the like condition.
         */
        private final String operand;

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
         * Appends the SQL representation of the {@link WhereCondition} to the provided {@code StringBuilder}.
         *
         * @param builder The {@code StringBuilder} to append the string representation onto.
         */
        @Override public void appendSQL(StringBuilder builder)
        {
            builder.append(String.format(" `%s` LIKE ?", column));
        }

        /**
         * Sets the parameters needed by the {@link WhereCondition}.
         *
         * @param statement      The statement to set the parameters upon.
         * @param parameterIndex The object containing the current index to set.
         * @throws SQLException When a database exception occurs.
         */
        @Override
        public void setParameters(PreparedStatement statement, Generator.ParameterIndex parameterIndex) throws SQLException
        {
            statement.setObject(parameterIndex.index++, operand);
        }
    }
}
