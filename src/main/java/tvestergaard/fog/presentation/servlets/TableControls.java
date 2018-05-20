package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.OrderDirection;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;

import static tvestergaard.fog.data.constraints.Constraint.like;

public class TableControls<C extends Enum<C> & Column<C>>
{

    private final HttpServletRequest request;
    private final Class<C>           columns;
    private final Parameters         parameters;
    private final C                  searchColumn;
    private final Constraints<C>     def;
    private       int                currentPage;

    public TableControls(HttpServletRequest request, Class<C> columns, C searchColumn, Constraints<C> def)
    {
        this.request = request;
        this.columns = columns;
        this.parameters = new Parameters(request);
        this.searchColumn = searchColumn;
        this.def = def;

        this.currentPage = parameters.isInt("page") ? parameters.getInt("page") : 1;


        def.limit(20);
        def.offset((currentPage - 1) * 20);

        request.setAttribute("orderings", EnumSet.allOf(OrderDirection.class));
        request.setAttribute("columns", EnumSet.allOf(columns));
        request.setAttribute("search_value", parameters.value("search_value"));
        request.setAttribute("sort_column", parameters.value("sort_column"));
        request.setAttribute("sort_direction", parameters.value("sort_direction"));
        request.setAttribute("page", currentPage);
    }

    public TableControls(HttpServletRequest request, Class<C> columns, C searchColumn)
    {
        this(request, columns, searchColumn, new Constraints<>());
    }

    public Constraints<C> constraints()
    {
        if (!parameters.isPresent("search"))
            return def;

        String         searchValue   = parameters.value("search_value");
        C              sortColumn    = parameters.getEnum("sort_column", columns);
        OrderDirection sortDirection = parameters.getEnum("sort_direction", OrderDirection.class);
        int            currentPage   = parameters.getInt("page");

        return new Constraints<C>()
                .having(like(searchColumn, '%' + searchValue + '%'))
                .order(sortColumn, sortDirection)
                .limit(20)
                .offset((currentPage - 1) * 20);
    }
}
