package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum MaterialColumn implements Column<MaterialColumn>, MysqlColumn
{

    ID,
    NUMBER,
    DESCRIPTION,
    PRICE,
    UNIT,
    CATEGORY,
    CREATED_AT,
    SEARCH;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return String.format("materials.%s", this.name().toLowerCase());
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override public boolean useBacktick()
    {
        if (this == SEARCH)
            return true;

        return false;
    }
}
