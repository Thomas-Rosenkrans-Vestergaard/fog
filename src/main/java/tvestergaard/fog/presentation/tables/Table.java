package tvestergaard.fog.presentation.tables;

import java.util.List;

public interface Table<T>
{

    /**
     * Returns the columns in the {@link Table}.
     *
     * @return The columns in the {@link Table}.
     */
    List<TableColumn<T>> getColumns();
}
