package tvestergaard.fog.data.constraints;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StatementBinder<T extends Column<T> & MysqlColumn>
{

    /**
     * The parameter currently being bound.
     */
    private int currentParameterIndex = 1;

    public void bind(PreparedStatement statement, int begin, Constraints<T> constraints) throws SQLException
    {
        if (constraints == null)
            return;

        currentParameterIndex = begin;

        bindWhereConditions(statement, constraints.getWhereConditions());
        bindWhereConditions(statement, constraints.getHavingConditions());
        bindLimit(statement, constraints.getLimitConstraint());
        bindOffset(statement, constraints.getOffsetConstraint());
    }

    private void bindLimit(PreparedStatement statement, int limit) throws SQLException
    {
        if (limit >= 0)
            statement.setInt(currentParameterIndex++, limit);
    }

    private void bindOffset(PreparedStatement statement, int offset) throws SQLException
    {
        if (offset >= 0)
            statement.setInt(currentParameterIndex++, offset);
    }

    private void bindWhereConditions(PreparedStatement statement, List<WhereCondition<T>> whereConditions) throws SQLException
    {
        if (whereConditions != null)
            for (WhereCondition<T> condition : whereConditions)
                bindWhereCondition(statement, condition);
    }

    public void bind(PreparedStatement statement, Constraints<T> constraints) throws SQLException
    {
        bind(statement, 1, constraints);
    }


    private void bindWhereCondition(PreparedStatement statement, WhereCondition condition) throws SQLException
    {

        if (condition instanceof BinaryOrCondition) {
            bindWhereCondition(statement, ((BinaryOrCondition) condition).left);
            bindWhereCondition(statement, ((BinaryOrCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryOrCondition) {
            bindWhereCondition(statement, ((UnaryOrCondition) condition).operand);
            return;
        }

        if (condition instanceof BinaryAndCondition) {
            bindWhereCondition(statement, ((BinaryAndCondition) condition).left);
            bindWhereCondition(statement, ((BinaryAndCondition) condition).right);
            return;
        }

        if (condition instanceof UnaryAndCondition) {
            bindWhereCondition(statement, ((UnaryAndCondition) condition).operand);
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
