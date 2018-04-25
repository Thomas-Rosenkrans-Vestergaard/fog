package tvestergaard.fog.presentation.tables;

import java.util.function.Function;

public class ColumnDefinition<T> implements TableColumn<T>
{

    /**
     * The header of the column.
     */
    private final String header;

    /**
     * The function that produces the column value.
     */
    private final Function<T, String> producer;

    /**
     * Creates a new {@link ColumnDefinition}.
     *
     * @param header   The header of the column.
     * @param producer The function that produces the column value.
     */
    public ColumnDefinition(String header, Function<T, String> producer)
    {
        this.header = header;
        this.producer = producer;
    }

    /**
     * Creates a new {@link ColumnDefinition}.
     *
     * @param header   The header of the column.
     * @param producer The function that produces the column value.
     */
    public static <T> ColumnDefinition of(String header, Function<T, Object> producer)
    {
        return new ColumnDefinition(header, producer);
    }

    /**
     * Returns the header of the {@link TableColumn}.
     *
     * @return The header of the {@link TableColumn}.
     */
    @Override public String getColumnHeader()
    {
        return header;
    }

    /**
     * Returns the column value for the provided row value.
     *
     * @param row The row.
     * @return The column value for the provided row value.
     */
    @Override public Object getColumnValue(T row)
    {
        return producer.apply(row);
    }
}
