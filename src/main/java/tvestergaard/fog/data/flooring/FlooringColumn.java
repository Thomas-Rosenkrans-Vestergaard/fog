package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum FlooringColumn implements Column<FlooringColumn>, MysqlColumn
{

    ID,
    NAME,
    DESCRIPTION,
    ACTIVE,
    SEARCH;

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
