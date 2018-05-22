package tvestergaard.fog.data.constraints;

public interface MysqlColumn<C> extends Column<C>
{

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    String getMysqlName();

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
