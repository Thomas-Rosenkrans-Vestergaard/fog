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
    SEARCH,
    ACTIVE;

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
     * Returns the column, that should be used in ORDER BY clauses. Used to order when using foreign keys.
     *
     * @return The column, that should be used in ORDER BY clauses.
     */
    @Override public String getOrderColumn()
    {
        if (this == CATEGORY)
            return "categories.name";

        return getMysqlName();
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
