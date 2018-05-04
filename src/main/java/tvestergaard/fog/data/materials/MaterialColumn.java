package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum MaterialColumn implements MysqlColumn
{

    ID,
    NUMBER,
    DESCRIPTION,
    NOTES,
    UNIT,
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
