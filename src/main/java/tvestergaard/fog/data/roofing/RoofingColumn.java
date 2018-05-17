package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum RoofingColumn implements Column<RoofingColumn>, MysqlColumn
{

    ID,
    NAME,
    DESCRIPTION,
    MINIMUM_SLOPE,
    MAXIMUM_SLOPE,
    ACTIVE,
    SEARCH;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override
    public String getMysqlName()
    {
        return this.name().toLowerCase();
    }
}
