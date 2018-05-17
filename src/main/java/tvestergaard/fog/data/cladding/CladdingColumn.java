package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum CladdingColumn implements Column<CladdingColumn>, MysqlColumn
{

    ID,
    NAME,
    DESCRIPTION,
    ACTIVE;

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
