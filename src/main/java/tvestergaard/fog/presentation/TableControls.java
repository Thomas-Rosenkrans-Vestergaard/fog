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

    private int page;

    public TableControls(HttpServletRequest request, Constraints<C> def)
    {
        this.parameters = new Parameters(request);
        request.setAttribute("parameters", parameters);
        request.setAttribute("columns", this.columns.entrySet());
        this.page = parameters.isInt("page") ? Math.max(1, parameters.getInt("page")) : 1;
        request.setAttribute("current_page", page);
        this.def = def;
    }

    public TableControls(HttpServletRequest request)
    {
        this(request, new Constraints<>());
    }

    public Constraints<C> constraints()
    {
        if (!parameters.isPresent("search"))
            return def.limit(20).offset((page - 1) * 20);

        Constraints<C> constraints = new Constraints<>();
        constraints.limit(20);
        constraints.offset((page - 1) * 20);
        for (Map.Entry<C, Type> column : columns.entrySet())
            if (column.getValue() == Type.BOOLEAN && parameters.isBoolean(column.getKey().name()))
                constraints.where(eq(column.getKey(), parameters.getBoolean(column.getKey().name())));
            else
                constraints.where(like(column.getKey(), '%' + parameters.value(column.getKey().name()) + '%'));
        return constraints;
    }
}
