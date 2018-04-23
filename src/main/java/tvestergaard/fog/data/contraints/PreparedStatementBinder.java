package tvestergaard.fog.data.contraints;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Binds the parameters of a provided {@code PreparedStatement} using the provided {@link Constraint}s.
 */
public class PreparedStatementBinder
{

    /**
     * The parameter currently being bound.
     */
    private int currentParameterIndex = 1;

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
}
