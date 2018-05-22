package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum RoofingColumn implements MysqlColumn<RoofingColumn>
{

    ID,
    NAME,
    DESCRIPTION,
    ACTIVE,
    TYPE;

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
