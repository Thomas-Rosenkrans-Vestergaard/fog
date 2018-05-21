package tvestergaard.fog.data.constraints;

public interface MysqlColumn
{

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    String getMysqlName();

    /**
     * Returns the column, that should be used in search and sort columns. Used to order when using foreign keys.
     *
     * @return The column, that should be used in search and sort columns.
     */
    default String getForeignColumn()
    {
        return getMysqlName();
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    default boolean useBacktick()
    {
        return true;
    }
}
