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

    private static final int RECORDS_PER_PAGE = 20;

    private final HttpServletRequest request;
    private final Class<C>           columns;
    private final Parameters         parameters;
    private final C                  searchColumn;

    public TableControls(HttpServletRequest request, Class<C> columns, C searchColumn)
    {
        this.request = request;
        this.columns = columns;
        this.parameters = new Parameters(request);
        this.searchColumn = searchColumn;

        request.setAttribute("orderings", EnumSet.allOf(OrderDirection.class));
        request.setAttribute("columns", EnumSet.allOf(columns));
        request.setAttribute("search_value", request.getParameter("search_value"));
        request.setAttribute("sort_column", request.getParameter("sort_column"));
        request.setAttribute("sort_direction", request.getParameter("sort_direction"));
    }

    public Constraints<C> constraints()
    {
        if (!parameters.isPresent("search"))
            return null;

        String         searchValue   = parameters.value("search_value");
        C              sortColumn    = parameters.getEnum("sort_column", columns);
        OrderDirection sortDirection = parameters.getEnum("sort_direction", OrderDirection.class);

        return new Constraints<C>()
                .having(like(searchColumn, '%' + searchValue + '%'))
                .order(sortColumn, sortDirection);
    }
}
