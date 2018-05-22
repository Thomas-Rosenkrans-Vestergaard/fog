package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum MaterialColumn implements Column<MaterialColumn>, MysqlColumn<MaterialColumn>
{

    ID,
    NUMBER,
    DESCRIPTION,
    PRICE,
    UNIT,
    CATEGORY,
    CATEGORY_NAME,
    CREATED_AT,
    ACTIVE;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        if (this == CATEGORY_NAME)
            return "categories.name";

        return String.format("materials.%s", this.name().toLowerCase());
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
