package tvestergaard.fog.presentation.tables;

import java.util.List;

public class HtmlTableRenderer<T> implements TableRenderer<T, StringBuilder>
{

    /**
     * Renders the table of the provided data using the provided argument.
     *
     * @param table   The table to render.
     * @param rows    The data to render in the table.
     * @param builder The argument to use. This argument is mostly used to save the results of the rendering.
     */
    @Override public void render(Table<T> table, List<T> rows, StringBuilder builder)
    {
        List<TableColumn<T>> columns = table.getColumns();
        builder.append("<table class='highlight'>");
        createHead(columns, builder);
        createBody(columns, rows, builder);
        builder.append("</table>");
    }

    private void createHead(List<TableColumn<T>> columns, StringBuilder builder)
    {
        builder.append("<thead>");
        for (TableColumn<T> column : columns) {
            builder.append("<th>");
            builder.append(column.getColumnHeader());
            builder.append("</th>");
        }
        builder.append("</thead>");
    }

    private void createBody(List<TableColumn<T>> columns, List<T> rows, StringBuilder builder)
    {
        builder.append("<tbody>");
        for (T row : rows) {
            builder.append("<tr>");
            for (TableColumn<T> column : columns) {
                builder.append("<td>");
                builder.append(column.getColumnValue(row));
                builder.append("</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</tbody>");
    }
}
