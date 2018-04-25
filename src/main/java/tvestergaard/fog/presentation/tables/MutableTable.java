package tvestergaard.fog.presentation.tables;

public interface MutableTable<T> extends Table<T>
{

    /**
     * Adds a new column to the {@link Table}.
     *
     * @param column The column to add to the table.
     */
    void add(TableColumn<T> column);
}
