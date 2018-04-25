package tvestergaard.fog.presentation.tables;

public interface TableColumn<T>
{

    /**
     * Returns the header of the {@link TableColumn}.
     *
     * @return The header of the {@link TableColumn}.
     */
    String getColumnHeader();

    /**
     * Returns the column value for the provided row value.
     *
     * @param row The row.
     * @return The column value for the provided row value.
     */
    Object getColumnValue(T row);
}
