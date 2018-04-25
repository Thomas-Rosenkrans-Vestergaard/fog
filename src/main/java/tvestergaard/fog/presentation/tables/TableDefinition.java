package tvestergaard.fog.presentation.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableDefinition<T> implements MutableTable<T>
{

    /**
     * The columns in the {@link TableDefinition}.
     */
    private final List<TableColumn<T>> columns = new ArrayList<>();

    /**
     * Adds a new column to the {@link Table}.
     *
     * @param column The column to add to the table.
     */
    @Override public void add(TableColumn<T> column)
    {
        this.columns.add(column);
    }

    /**
     * Returns the columns in the {@link Table}.
     *
     * @return The columns in the {@link Table}.
     */
    @Override public List<TableColumn<T>> getColumns()
    {
        return Collections.unmodifiableList(this.columns);
    }
}
