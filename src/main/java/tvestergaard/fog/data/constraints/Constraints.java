package tvestergaard.fog.data.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constraints<C extends Column<C>>
{

    private List<WhereCondition<C>> whereConditions;
    private List<C>                 groupConstraint;
    private List<WhereCondition<C>> havingConditions;
    private Map<C, OrderDirection> orderColumns     = new HashMap<>();
    private List<String>           orderSQL         = new ArrayList<>();
    private int                    limitConstraint  = -1;
    private int                    offsetConstraint = -1;

    public Constraints<C> where(WhereCondition<C>... conditions)
    {
        if (whereConditions == null)
            whereConditions = new ArrayList<>();

        for (WhereCondition<C> condition : conditions)
            whereConditions.add(condition);

        return this;
    }

    public Constraints<C> groupBy(C... columns)
    {
        if (groupConstraint == null)
            groupConstraint = new ArrayList<>();

        for (C column : columns)
            groupConstraint.add(column);

        return this;
    }

    public Constraints<C> having(WhereCondition<C>... conditions)
    {
        if (havingConditions == null)
            havingConditions = new ArrayList<>();

        for (WhereCondition<C> condition : conditions)
            havingConditions.add(condition);

        return this;
    }

    public Constraints<C> order(C column, OrderDirection direction)
    {
        if (orderColumns == null)
            orderColumns = new HashMap<>();

        orderColumns.put(column, direction);

        return this;
    }

    public Constraints<C> order(String directive)
    {
        if (orderSQL == null)
            orderSQL = new ArrayList<>();

        orderSQL.add(directive);

        return this;
    }

    public Constraints<C> limit(int limit)
    {
        this.limitConstraint = limit;

        return this;
    }

    public Constraints<C> offset(int offset)
    {
        this.offsetConstraint = offset;
        return this;
    }

    public List<WhereCondition<C>> getWhereConditions()
    {
        return this.whereConditions;
    }

    public List<C> getGroupConstraint()
    {
        return this.groupConstraint;
    }

    public List<WhereCondition<C>> getHavingConditions()
    {
        return this.havingConditions;
    }

    public Map<C, OrderDirection> getOrderColumns()
    {
        return this.orderColumns;
    }

    public List<String> getOrderSQL()
    {
        return this.orderSQL;
    }

    public int getLimitConstraint()
    {
        return this.limitConstraint;
    }

    public int getOffsetConstraint()
    {
        return this.offsetConstraint;
    }
}
