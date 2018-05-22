package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OrderColumn implements Column<OrderColumn>, MysqlColumn<OrderColumn>
{

    ID,
    CUSTOMER,
    CUSTOMER_NAME,
    CLADDING,
    WIDTH,
    LENGTH,
    HEIGHT,
    ROOFING,
    ROOFING_NAME,
    SLOPE,
    RAFTERS,
    ACTIVE,
    OPEN_OFFERS,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        if (this == CUSTOMER_NAME)
            return "customers.name";

        if(this == ROOFING_NAME)
            return "roofings.name";

        return String.format("o.%s", this.name().toLowerCase());
    }

    /**
     * Whether or not the generator should use backtick (`) on the column name.
     *
     * @return {@code true} if the generator should use backtick (`) on the column name.
     */
    @Override public boolean useBacktick()
    {
        if (this == OPEN_OFFERS)
            return true;

        return false;
    }
}
