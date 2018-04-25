package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum FlooringColumn implements MysqlColumn
{

    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE_PER_METER("price_per_square_meter"),
    ACTIVE("active");

    /**
     * The name of the column in SQL.
     */
    private final String mysqlName;

    /**
     * Creates a new {@link CladdingColumn}.
     *
     * @param mysqlName The name of the column in SQL.
     */
    FlooringColumn(String mysqlName)
    {
        this.mysqlName = mysqlName;
    }

    /**
     * Returns the name of the column in mysql.
     *
     * @return The name of the column in mysql.
     */
    @Override public String getMysqlName()
    {
        return mysqlName;
    }
}