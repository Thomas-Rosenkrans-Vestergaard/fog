package tvestergaard.fog.data.constraints;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Generated a prepared statement from the provided {@link Constraint}s.
 */
public class StatementGenerator<T extends Enum<T> & MysqlColumn>
{

    /**
     * Contains the ordering of contains.
     *
     * @see StatementGenerator#sort(Constraint[])
     */
    private final java.util.Map<Class<? extends Constraint>, Integer> constraintOrder = new HashMap<>();

    public StatementGenerator()
    {
        constraintOrder.put(WhereConstraint.class, 0);
        constraintOrder.put(OrderConstraint.class, 1);
        constraintOrder.put(LimitConstraint.class, 2);
        constraintOrder.put(OffsetConstraint.class, 4);
    }

    /**
     * Generates the SQL representing the provided {@link Constraint}s. Returns the generated SQL appended to the
     * provided {@code statement}.
     *
     * @param statement   The base statement.
     * @param constraints The constraints to generate into SQL.
     * @return The SQL representing the provided {@link Constraint}s appended to the provided {@code statement}.
     */
    public String generate(String statement, Constraint<T>... constraints)
    {
        return statement + generate(constraints);
    }

    /**
     * Generates and returns the SQL representing the provided {@link Constraint}s.
     *
     * @param constraints The constraints to generate into SQL.
     * @return The SQL representing the provided {@link Constraint}s.
     */
    public String generate(Constraint<T>... constraints)
    {
        StringBuilder builder = new StringBuilder("");
        sort(constraints);
        for (Constraint constraint : constraints)
            appendConstraintSQL(builder, constraint);

        return builder.toString();
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
     * Appends the SQL, needed to represent the provided {@link Constraint}, to the provided {@code StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link Constraint} to append the SQL representation of.
     */
    private void appendConstraintSQL(StringBuilder builder, Constraint<T> constraint)
    {
        if (constraint instanceof WhereConstraint) {
            appendWhereConstraintSQL(builder, (WhereConstraint) constraint);
            return;
        }

        if (constraint instanceof OrderConstraint) {
            appendOrderConstraintSQL(builder, (OrderConstraint) constraint);
            return;
        }

        if (constraint instanceof LimitConstraint) {
            appendLimitConstraint(builder, (LimitConstraint) constraint);
            return;
        }

        if (constraint instanceof OffsetConstraint) {
            appendOffsetConstraint(builder, (OffsetConstraint) constraint);
            return;
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link WhereConstraint}, to the provided {@code
     * StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link Constraint} to append the SQL representation of.
     */
    private void appendWhereConstraintSQL(StringBuilder builder, WhereConstraint<T> constraint)
    {
        WhereCondition[] conditions = constraint.getConditions();
        if (conditions.length > 0) {
            builder.append(" WHERE");
            for (WhereCondition condition : constraint.getConditions())
                appendWhereConditionSQL(builder, condition);
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link WhereCondition}, to the provided {@code StringBuilder}.
     *
     * @param builder   The {@code StringBuilder} to append the SQL to.
     * @param condition The {@link WhereCondition} to append the SQL representation of.
     */
    private void appendWhereConditionSQL(StringBuilder builder, WhereCondition<T> condition)
    {
        if (condition instanceof BinaryAndCondition) {
            builder.append(' ');
            builder.append('(');
            appendWhereConditionSQL(builder, ((BinaryAndCondition) condition).left);
            builder.append(" AND");
            appendWhereConditionSQL(builder, ((BinaryAndCondition) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            builder.append(" AND");
            appendWhereConditionSQL(builder, ((UnaryAndCondition) condition).operand);
            return;
        }

        if (condition instanceof BinaryOrCondition) {
            builder.append(' ');
            builder.append('(');
            appendWhereConditionSQL(builder, ((BinaryOrCondition) condition).left);
            builder.append(" OR");
            appendWhereConditionSQL(builder, ((BinaryOrCondition) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            builder.append(" OR");
            appendWhereConditionSQL(builder, ((UnaryOrCondition) condition).operand);
            return;
        }

        if (condition instanceof EqualsCondition) {
            appendEqualsConditionSQL(builder, (EqualsCondition) condition);
            return;
        }

        if (condition instanceof NotCondition) {
            appendNotConditionSQL(builder, (NotCondition) condition);
            return;
        }

        if (condition instanceof LikeCondition) {
            appendLikeConditionSQL(builder, (LikeCondition) condition);
            return;
        }
    }

    private void appendNotConditionSQL(StringBuilder builder, NotCondition<T> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" != ?");
    }

    private void appendEqualsConditionSQL(StringBuilder builder, EqualsCondition<T> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" = ?");
    }

    private void appendLikeConditionSQL(StringBuilder builder, LikeCondition<T> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" LIKE ?");
    }

    /**
     * Appends the SQL, needed to represent the provided {@link OrderConstraint}, to the provided {@code
     * StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link OrderConstraint} to append the SQL representation of.
     */
    private void appendOrderConstraintSQL(StringBuilder builder, OrderConstraint<T> constraint)
    {
        builder.append(" ORDER BY ");
        if (constraint.getColumn().useBacktick())
            builder.append('`');
        builder.append(constraint.getColumn().getMysqlName());
        if (constraint.getColumn().useBacktick())
            builder.append('`');
        builder.append(' ');
        builder.append(constraint.getDirection());
    }

    /**
     * Appends the SQL, needed to represent the provided {@link LimitConstraint}, to the provided {@code
     * StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link LimitConstraint} to append the SQL representation of.
     */
    private void appendLimitConstraint(StringBuilder builder, LimitConstraint<T> constraint)
    {
        builder.append(" LIMIT ?");
    }

    /**
     * Appends the SQL, needed to represent the provided {@link OffsetConstraint}, to the provided {@code
     * StringBuilder}.
     *
     * @param builder    The {@code StringBuilder} to append the SQL to.
     * @param constraint The {@link OffsetConstraint} to append the SQL representation of.
     */
    private void appendOffsetConstraint(StringBuilder builder, OffsetConstraint<T> constraint)
    {
        builder.append(" OFFSET ?");
    }
}
