package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum CladdingColumn implements MysqlColumn
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
    CladdingColumn(String mysqlName)
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
