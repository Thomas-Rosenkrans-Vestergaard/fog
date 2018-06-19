package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum CustomerColumn implements Column<CustomerColumn>, MysqlColumn<CustomerColumn>
{

    ID,
    NAME,
    ADDRESS,
    EMAIL,
    PHONE,
    PASSWORD,
    ACTIVE,
    VERIFIED,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return this.name().toLowerCase();
    }
}
