package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum RoofingColumn implements MysqlColumn
{

    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    MINIMUM_SLOPE("minimum_slope"),
    MAXIMUM_SLOPE("maximum_slope"),
    PRICE_PER_SQUARE_METER("price_per_square_meter"),
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
    RoofingColumn(String mysqlName)
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
