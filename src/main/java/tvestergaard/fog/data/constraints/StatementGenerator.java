package tvestergaard.fog.data.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatementGenerator<C extends Column<C> & MysqlColumn>
{

    public String generate(String statement, Constraints<C> constraints)
    {
        return statement + generate(constraints);
    }

    public String generate(Constraints<C> constraints)
    {
        if (constraints == null)
            return "";

        StringBuilder builder = new StringBuilder("");

        appendWhereConstraintSQL(builder, constraints.getWhereConditions());
        appendGroupByConstraintSQL(builder, constraints.getGroupConstraint());
        appendHavingConstraintSQL(builder, constraints.getHavingConditions());
        appendOrderConstraintSQL(builder, constraints.getOrderColumns(), constraints.getOrderSQL());
        appendLimitConstraint(builder, constraints.getLimitConstraint());
        appendOffsetConstraint(builder, constraints.getOffsetConstraint());

        return builder.toString();
    }

    private void appendWhereConstraintSQL(StringBuilder builder, List<WhereCondition<C>> conditions)
    {
        if (conditions != null && conditions.size() > 0) {
            builder.append(" WHERE");
            for (WhereCondition condition : conditions)
                appendWhereConditionSQL(builder, condition);
        }
    }

    private void appendHavingConstraintSQL(StringBuilder builder, List<WhereCondition<C>> conditions)
    {
        if (conditions != null && conditions.size() > 0) {
            builder.append(" HAVING");
            for (WhereCondition condition : conditions)
                appendWhereConditionSQL(builder, condition);
        }
    }

    /**
     * Appends the SQL, needed to represent the provided {@link WhereCondition}, to the provided {@code StringBuilder}.
     *
     * @param builder   The {@code StringBuilder} to append the SQL to.
     * @param condition The {@link WhereCondition} to append the SQL representation of.
     */
    private void appendWhereConditionSQL(StringBuilder builder, WhereCondition<C> condition)
    {
        if (condition instanceof BinaryAndCondition) {
            builder.append(' ');
            builder.append('(');
            appendWhereConditionSQL(builder, ((BinaryAndCondition<C>) condition).left);
            builder.append(" AND");
            appendWhereConditionSQL(builder, ((BinaryAndCondition<C>) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            builder.append(" AND");
            appendWhereConditionSQL(builder, ((UnaryAndCondition<C>) condition).operand);
            return;
        }

        if (condition instanceof BinaryOrCondition) {
            builder.append(' ');
            builder.append('(');
            appendWhereConditionSQL(builder, ((BinaryOrCondition<C>) condition).left);
            builder.append(" OR");
            appendWhereConditionSQL(builder, ((BinaryOrCondition<C>) condition).right);
            builder.append(')');
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            builder.append(" OR");
            appendWhereConditionSQL(builder, ((UnaryOrCondition<C>) condition).operand);
            return;
        }

        if (condition instanceof EqualsCondition) {
            appendEqualsConditionSQL(builder, (EqualsCondition<C>) condition);
            return;
        }

        if (condition instanceof NotCondition) {
            appendNotConditionSQL(builder, (NotCondition<C>) condition);
            return;
        }

        if (condition instanceof LikeCondition) {
            appendLikeConditionSQL(builder, (LikeCondition<C>) condition);
            return;
        }

        if (condition instanceof InCondition) {
            appendInConditionSQL(builder, (InCondition<C>) condition);
            return;
        }
    }

    private void appendInConditionSQL(StringBuilder builder, InCondition<C> condition)
    {
        builder.append(' ');
        if (condition.getColumn().useBacktick())
            builder.append('`');
        builder.append(condition.getColumn().getMysqlName());
        if (condition.getColumn().useBacktick())
            builder.append('`');

        int arguments = condition.getArguments().length;

        builder.append(" IN (");
        if (arguments > 0)
            builder.append('?');
        for (int argument = 1; argument < arguments; argument++) {
            builder.append(',');
            builder.append('?');
        }

        builder.append(')');
    }

    private void appendNotConditionSQL(StringBuilder builder, NotCondition<C> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" != ?");
    }

    private void appendEqualsConditionSQL(StringBuilder builder, EqualsCondition<C> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" = ?");
    }

    private void appendLikeConditionSQL(StringBuilder builder, LikeCondition<C> condition)
    {
        builder.append(' ');
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(condition.column.getMysqlName());
        if (condition.column.useBacktick())
            builder.append('`');
        builder.append(" LIKE ?");
    }

    private void appendGroupByConstraintSQL(StringBuilder builder, List<C> groupConstraint)
    {
        if (groupConstraint != null && groupConstraint.size() > 0) {
            builder.append(" GROUP BY ");
            Iterator<C> it = groupConstraint.iterator();
            appendColumn(builder, it.next());
            while (it.hasNext()) {
                builder.append(", ");
                appendColumn(builder, it.next());
            }
        }
    }

    private void appendColumn(StringBuilder builder, C column)
    {
        builder.append(' ');
        if (column.useBacktick())
            builder.append('`');
        builder.append(column.getMysqlName());
        if (column.useBacktick())
            builder.append('`');
    }

    private void appendOrderConstraintSQL(StringBuilder builder, Map<C, OrderDirection> orderColumns, List<String> orderSQL)
    {
        boolean first = true;

        if ((orderColumns != null && orderColumns.size() > 0) || (orderSQL != null && orderSQL.size() > 0))
            builder.append(" ORDER BY ");
        if (orderColumns != null && orderColumns.size() > 0)
            for (Map.Entry<C, OrderDirection> orderColumn : orderColumns.entrySet()) {
                if (!first)
                    builder.append(',');
                appendOrderColumnSQL(builder, orderColumn.getKey(), orderColumn.getValue());
                first = false;
            }

        if (orderSQL != null && orderSQL.size() > 0) {
            for (String sql : orderSQL) {
                if (!first)
                    builder.append(", ");
                builder.append(sql.trim());
                first = false;
            }
        }
    }

    private void appendOrderColumnSQL(StringBuilder builder, C column, OrderDirection direction)
    {
        builder.append(' ');
        if (column.useBacktick())
            builder.append('`');
        builder.append(column.getForeignColumn());
        if (column.useBacktick())
            builder.append('`');
        builder.append(" ");
        builder.append(direction.name());
    }

    private void appendLimitConstraint(StringBuilder builder, int limit)
    {
        if (limit >= 0)
            builder.append(" LIMIT ?");
    }

    private void appendOffsetConstraint(StringBuilder builder, int offset)
    {
        if (offset >= 0)
            builder.append(" OFFSET ?");
    }
}
