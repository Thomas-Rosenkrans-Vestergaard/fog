package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OrderColumn implements MysqlColumn
{

    ID("orders.id"),
    CUSTOMER("orders.customer"),
    TYPE("orders.type"),
    CLADDING("orders.cladding"),
    WIDTH("orders.width"),
    LENGTH("orders.length"),
    HEIGHT("orders.height"),
    ROOFING("orders.roofing"),
    SLOPE("orders.slope");

    /**
     * The name of the column in SQL.
     */
    private final String mysqlName;

    /**
     * Creates a new {@link CladdingColumn}.
     *
     * @param mysqlName The name of the column in SQL.
     */
    OrderColumn(String mysqlName)
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
