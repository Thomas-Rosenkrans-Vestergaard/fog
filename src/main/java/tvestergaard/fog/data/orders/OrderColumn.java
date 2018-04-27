package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OrderColumn implements MysqlColumn
{

    ID,
    CUSTOMER,
    CLADDING,
    WIDTH,
    LENGTH,
    HEIGHT,
    ROOFING,
    SLOPE,
    RAFTERS;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override
    public String getMysqlName()
    {
        return String.format("o.%s", this.name().toLowerCase());
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override
    public boolean useBacktick()
    {
        return false;
    }
}
