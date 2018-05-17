package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.OrderDirection;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;

import static tvestergaard.fog.data.constraints.Constraint.like;

public class TableControls<T extends Enum<T> & Column<T>>
{

    private static final int RECORDS_PER_PAGE = 20;

    private final HttpServletRequest request;
    private final Class<T>           columns;
    private final Parameters         parameters;

    public TableControls(HttpServletRequest request, Class<T> columns, int numberOfRecords)
    {
        this.request = request;
        this.columns = columns;
        this.parameters = new Parameters(request);

        request.setAttribute("orderings", EnumSet.allOf(OrderDirection.class));
        request.setAttribute("columns", EnumSet.allOf(columns));
        request.setAttribute("search_column", request.getParameter("search_column"));
        request.setAttribute("search_value", request.getParameter("search_value"));
        request.setAttribute("sort_column", request.getParameter("sort_column"));
        request.setAttribute("sort_direction", request.getParameter("sort_direction"));
        request.setAttribute("number_of_pages", (int) Math.ceil((double) numberOfRecords / RECORDS_PER_PAGE));
        request.setAttribute("current_page", parameters.isInt("page") ? parameters.getInt("page") : 1);
    }

    public Constraints<T> constraints()
    {
        if (!parameters.isEnum("search_column", columns)) {
            return new Constraints<>();
        }

        T              searchColumn  = parameters.getEnum("search_column", columns);
        String         searchValue   = parameters.value("search_value");
        T              sortColumn    = parameters.getEnum("sort_column", columns);
        OrderDirection sortDirection = parameters.getEnum("sort_direction", OrderDirection.class);

        return new Constraints<>()
                .where(like(searchColumn, '%' + searchValue + '%'))
                .order(sortColumn, sortDirection);
    }
}
