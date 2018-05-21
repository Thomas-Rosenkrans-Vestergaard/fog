package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OrderColumn implements Column<OrderColumn>, MysqlColumn
{

    ID,
    CUSTOMER,
    CLADDING,
    WIDTH,
    LENGTH,
    HEIGHT,
    ROOFING,
    SLOPE,
    RAFTERS,
    ACTIVE,
    SEARCH,
    OPEN_OFFERS,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return String.format("o.%s", this.name().toLowerCase());
    }

    /**
     * Returns the column, that should be used in ORDER BY clauses. Used to order when using foreign keys.
     *
     * @return The column, that should be used in ORDER BY clauses.
     */
    @Override public String getForeignColumn()
    {
        if (this == CUSTOMER)
            return "customers.name";

        return getMysqlName();
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override public boolean useBacktick()
    {
        if (this == SEARCH || this == OPEN_OFFERS)
            return true;

        return false;
    }
}
