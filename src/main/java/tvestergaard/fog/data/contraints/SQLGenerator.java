package tvestergaard.fog.data.contraints;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Generates SQL for prepared statements representing the provided constraints. Binds SQL statements for prepared statements
 * for the provided constraints.
 */
public class SQLGenerator
{

    /**
     * The parameter index currently being set.
     */
    private int currentParameterIndex;

    /**
     * Appends the SQL representation of the provided {@link Constraint}s to the provided {@code statement}.
     *
     * @param statement   The statement to append the SQL representation of the provided {@link Constraint}s to.
     * @param constraints The {@link Constraint}s to add to the provided {@code statement}.
     * @return The resulting statement.
     */
    public String generateSQL(String statement, Constraint... constraints)
    {
        StringBuilder builder = new StringBuilder(statement);
        for (Constraint constraint : constraints)
            appendSQL(builder, constraint);

        return builder.toString();
    }

    public String generateSQL(Constraint... constraints)
    {
        return generateSQL("", constraints);
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link Constraint}s.
     *
     * @param statement   The statement to set the prepared parameters on.
     * @param begin       The index of the parameter to start setting prepared parameters from.
     * @param constraints The {@link Constraint}s to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    public void setParameters(PreparedStatement statement, int begin, Constraint... constraints) throws SQLException
    {
        currentParameterIndex = begin;
        for (Constraint constraint : constraints) {
            setParameters(statement, constraint);
        }
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link Constraint}.
     *
     * @param statement  The statement to set the prepared parameters on.
     * @param constraint The {@link Constraint} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void setParameters(PreparedStatement statement, Constraint constraint) throws SQLException
    {
        if (constraint instanceof WhereConstraint) {
            setParameters(statement, (WhereConstraint) constraint);
            return;
        }

        if (constraint instanceof LimitConstraint) {
            statement.setInt(currentParameterIndex++, ((LimitConstraint) constraint).limit);
            return;
        }

        if (constraint instanceof OffsetConstraint) {
            statement.setInt(currentParameterIndex++, ((OffsetConstraint) constraint).offset);
            return;
        }
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link WhereConstraint}.
     *
     * @param statement  The statement to set the prepared parameters on.
     * @param constraint The {@link Constraint} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void setParameters(PreparedStatement statement, WhereConstraint constraint) throws SQLException
    {
        for (WhereCondition condition : constraint.getConditions()) {
            setParameters(statement, condition);
        }
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link WhereConstraint}.
     *
     * @param statement The statement to set the prepared parameters on.
     * @param condition The {@link WhereCondition} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void setParameters(PreparedStatement statement, WhereCondition condition) throws SQLException
    {

        if (condition instanceof BinaryOrCondition) {
            setParameters(statement, ((BinaryOrCondition) condition).left);
            setParameters(statement, ((BinaryOrCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            setParameters(statement, ((UnaryOrCondition) condition).operand);
            return;
        }

        if (condition instanceof BinaryAndCondition) {
            setParameters(statement, ((BinaryAndCondition) condition).left);
            setParameters(statement, ((BinaryAndCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            setParameters(statement, ((UnaryAndCondition) condition).operand);
            return;
        }

        if (condition instanceof EqualsCondition) {
            statement.setObject(currentParameterIndex++, ((EqualsCondition) condition).value);
            return;
        }

        if (condition instanceof LikeCondition) {
            statement.setObject(currentParameterIndex++, ((LikeCondition) condition).operand);
            return;
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link Constraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link Constraint} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, Constraint constraint)
    {
        if (constraint instanceof WhereConstraint) {
            appendSQL(builder, (WhereConstraint) constraint);
            return;
        }

        if (constraint instanceof OrderConstraint) {
            appendSQL(builder, (OrderConstraint) constraint);
            return;
        }

        if (constraint instanceof LimitConstraint) {
            appendSQL(builder, (LimitConstraint) constraint);
            return;
        }

        if (constraint instanceof OffsetConstraint) {
            appendSQL(builder, (OffsetConstraint) constraint);
            return;
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link WhereConstraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link Constraint} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, WhereConstraint constraint)
    {
        WhereCondition[] conditions = constraint.getConditions();
        if (conditions.length > 0) {
            builder.append(" WHERE");
            for (WhereCondition condition : constraint.getConditions())
                appendSQL(builder, condition);
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link WhereCondition}, to the provided {@code StringBuilder}.
     *
     * @param builder   The {@code StringBuilder} to append the SQL to.
     * @param condition The {@link WhereCondition} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, WhereCondition condition)
    {
        if (condition instanceof BinaryAndCondition) {
            builder.append(' ');
            builder.append('(');
            appendSQL(builder, ((BinaryAndCondition) condition).left);
            builder.append(" AND");
            appendSQL(builder, ((BinaryAndCondition) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            builder.append(" AND");
            appendSQL(builder, ((UnaryAndCondition) condition).operand);
            return;
        }

        if (condition instanceof BinaryOrCondition) {
            builder.append(' ');
            builder.append('(');
            appendSQL(builder, ((BinaryOrCondition) condition).left);
            builder.append(" OR");
            appendSQL(builder, ((BinaryOrCondition) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            builder.append(" OR");
            appendSQL(builder, ((UnaryOrCondition) condition).operand);
            return;
        }

        if (condition instanceof EqualsCondition) {
            builder.append(String.format(" `%s` = ?", ((EqualsCondition) condition).column));
            return;
        }

        if (condition instanceof LikeCondition) {
            builder.append(String.format(" `%s` LIKE ?", ((LikeCondition) condition).column));
            return;
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link OrderConstraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link OrderConstraint} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, OrderConstraint constraint)
    {
        builder.append(" ORDER BY `");
        builder.append(constraint.getColumn());
        builder.append("` ");
        builder.append(constraint.getDirection());
    }

    /**
     * Appends the SQL, needed to represent the provided {@link LimitConstraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link LimitConstraint} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, LimitConstraint constraint)
    {
        builder.append(" LIMIT ?");
    }

    /**
     * Appends the SQL, needed to represent the provided {@link OffsetConstraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link OffsetConstraint} to append the SQL representation of.
     */
    private void appendSQL(StringBuilder builder, OffsetConstraint constraint)
    {
        builder.append(" OFFSET ?");
    }
}
