package tvestergaard.fog.presentation.tables;

import java.util.List;

public interface TableRenderer<T, A>
{

    /**
     * Renders the table of the provided data using the provided argument.
     *
     * @param table    The table to render.
     * @param rows     The data to render in the table.
     * @param argument The argument to use. This argument is mostly used to save the results of the rendering.
     */
    void render(Table<T> table, List<T> rows, A argument);
}
