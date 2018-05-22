package tvestergaard.fog.presentation;

import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.MysqlColumn;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.like;

public class TableControls<C extends Enum<C> & MysqlColumn<C>>
{

    private final Parameters     parameters;
    private final Constraints<C> def;

    public enum Type
    {
        TEXT,
        INT,
        BOOLEAN,
        TIMESTAMP
    }

    private final Map<C, Type> columns = new LinkedHashMap<>();

    public void add(C column, Type type)
    {
        columns.put(column, type);
    }

    public TableControls(HttpServletRequest request, Constraints<C> def)
    {
        this.parameters = new Parameters(request);
        request.setAttribute("parameters", parameters);
        request.setAttribute("columns", this.columns.entrySet());
        this.def = def;
    }

    public TableControls(HttpServletRequest request)
    {
        this(request, new Constraints<>());
    }

    public Constraints<C> constraints()
    {
        if (!parameters.isPresent("search"))
            return def;

        Constraints<C> constraints = new Constraints<>();
        for (Map.Entry<C, Type> column : columns.entrySet())
            if (column.getValue() == Type.BOOLEAN && parameters.isBoolean(column.getKey().getMysqlName()))
                constraints.where(eq(column.getKey(), parameters.getBoolean(column.getKey().getMysqlName())));
            else
                constraints.where(like(column.getKey(), '%' + parameters.value(column.getKey().getMysqlName()) + '%'));
        return constraints;
    }
}
