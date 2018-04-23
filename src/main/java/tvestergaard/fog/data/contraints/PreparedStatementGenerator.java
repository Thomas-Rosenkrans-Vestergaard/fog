package tvestergaard.fog.data.contraints;

/**
 * Generated a prepared statement from the provided {@link Constraint}s.
 */
public class PreparedStatementGenerator
{

    /**
     * Generates the SQL representing the provided {@link Constraint}s. Returns the generated SQL appended to the provided
     * {@code statement}.
     *
     * @param statement   The base statement.
     * @param constraints The constraints to generate into SQL.
     * @return The SQL representing the provided {@link Constraint}s appended to the provided {@code statement}.
     */
    public String generate(String statement, Constraint... constraints)
    {
        return statement + generate(constraints);
    }

    /**
     * Generates and returns the SQL representing the provided {@link Constraint}s.
     *
     * @param constraints The constraints to generate into SQL.
     * @return The SQL representing the provided {@link Constraint}s.
     */
    public String generate(Constraint... constraints)
    {
        StringBuilder builder = new StringBuilder("");
        for (Constraint constraint : constraints)
            appendSQL(builder, constraint);

        return builder.toString();
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
