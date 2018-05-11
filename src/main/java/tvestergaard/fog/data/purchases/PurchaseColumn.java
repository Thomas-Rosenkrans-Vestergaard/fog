package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum PurchaseColumn implements MysqlColumn
{
    ID,
    OFFER,
    EMPLOYEE,
    BOM,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return String.format("purchases.%s", this.name().toLowerCase());
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override public boolean useBacktick()
    {
        return false;
    }
}
