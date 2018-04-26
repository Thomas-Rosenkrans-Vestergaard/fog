package tvestergaard.fog.data.constraints;

public interface MysqlColumn
{

    /**
     * Returns the name of the column in mysql.
     *
     * @return The name of the column in mysql.
     */
    String getSQLName();

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
