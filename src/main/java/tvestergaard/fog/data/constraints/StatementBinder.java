package tvestergaard.fog.data.constraints;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Binds the parameters of a provided {@code PreparedStatement} using the provided {@link Constraint}s.
 */
public class StatementBinder<T extends Enum<T> & MysqlColumn>
{

    /**
     * The parameter currently being bound.
     */
    private int currentParameterIndex = 1;

    /**
     * Contains the ordering of contains.
     *
     * @see StatementGenerator#sort(Constraint[])
     */
    private final java.util.Map<Class<? extends Constraint>, Integer> constraintOrder = new HashMap<>();

    public StatementBinder()
    {
        constraintOrder.put(WhereConstraint.class, 0);
        constraintOrder.put(OrderConstraint.class, 1);
        constraintOrder.put(LimitConstraint.class, 2);
        constraintOrder.put(OffsetConstraint.class, 4);
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link
     * Constraint}s.
     *
     * @param statement   The statement to set the prepared parameters on.
     * @param begin       The index of the parameter to start setting prepared parameters from.
     * @param constraints The {@link Constraint}s to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    public void bind(PreparedStatement statement, int begin, Constraint<T>... constraints) throws SQLException
    {
        currentParameterIndex = begin;
        sort(constraints);
        for (Constraint constraint : constraints) {
            bind(statement, constraint);
        }
    }

    /**
     * Sorts the provided {@link Constraint}s, so they appear in the order required by SQL.
     *
     * @param constraints The constraints to sort. Mutates the provided array.
     */
    private void sort(Constraint<T>[] constraints)
    {
        Arrays.sort(constraints, Comparator.comparingInt(c -> constraintOrder.get(c.getClass())));
    }


    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link
     * Constraint}s. Begins binding from parameter index 1.
     *
     * @param statement   The statement to set the prepared parameters on.
     * @param constraints The {@link Constraint}s to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    public void bind(PreparedStatement statement, Constraint<T>... constraints) throws SQLException
    {
        bind(statement, 1, constraints);
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link
     * Constraint}.
     *
     * @param statement  The statement to set the prepared parameters on.
     * @param constraint The {@link Constraint} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void bind(PreparedStatement statement, Constraint constraint) throws SQLException
    {
        if (constraint instanceof WhereConstraint) {
            bind(statement, (WhereConstraint) constraint);
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
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link
     * WhereConstraint}.
     *
     * @param statement  The statement to set the prepared parameters on.
     * @param constraint The {@link Constraint} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void bind(PreparedStatement statement, WhereConstraint constraint) throws SQLException
    {
        for (WhereCondition condition : constraint.getConditions()) {
            bind(statement, condition);
        }
    }

    /**
     * Sets the prepared parameters on the provided {@link PreparedStatement} needed by the provided {@link
     * WhereConstraint}.
     *
     * @param statement The statement to set the prepared parameters on.
     * @param condition The {@link WhereCondition} to set the prepared parameters of.
     * @throws SQLException When a database exception occurs.
     */
    private void bind(PreparedStatement statement, WhereCondition condition) throws SQLException
    {

        if (condition instanceof BinaryOrCondition) {
            bind(statement, ((BinaryOrCondition) condition).left);
            bind(statement, ((BinaryOrCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            bind(statement, ((UnaryOrCondition) condition).operand);
            return;
        }

        if (condition instanceof BinaryAndCondition) {
            bind(statement, ((BinaryAndCondition) condition).left);
            bind(statement, ((BinaryAndCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            bind(statement, ((UnaryAndCondition) condition).operand);
            return;
        }

        if (condition instanceof EqualsCondition) {
            statement.setObject(currentParameterIndex++, ((EqualsCondition) condition).value);
            return;
        }

        if (condition instanceof NotCondition) {
            statement.setObject(currentParameterIndex++, ((NotCondition) condition).value);
        }

        if (condition instanceof LikeCondition) {
            statement.setObject(currentParameterIndex++, ((LikeCondition) condition).operand);
            return;
        }
    }
}
