package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OrderColumn implements MysqlColumn
{

    ID("o.id"),
    CUSTOMER("o.customer"),
    CLADDING("o.cladding"),
    WIDTH("o.width"),
    LENGTH("o.length"),
    HEIGHT("o.height"),
    ROOFING("o.roofing"),
    SLOPE("o.slope"),
    RAFTERS("o.rafters");

    /**
     * The name of the column in SQL.
     */
    private final String SQLName;

    /**
     * Creates a new {@link CladdingColumn}.
     *
     * @param SQLName The name of the column in SQL.
     */
    OrderColumn(String SQLName)
    {
        this.SQLName = SQLName;
    }

    /**
     * Returns the name of the column in mysql.
     *
     * @return The name of the column in mysql.
     */
    @Override
    public String getSQLName()
    {
        return SQLName;
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override
    public boolean useBacktick()
    {
        return false;
    }
}
