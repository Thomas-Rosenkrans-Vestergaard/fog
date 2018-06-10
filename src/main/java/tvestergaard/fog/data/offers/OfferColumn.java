package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OfferColumn implements Column<OfferColumn>, MysqlColumn<OfferColumn>
{

    ID,
    ORDER,
    EMPLOYEE,
    EMPLOYEE_NAME,
    CUSTOMER,
    CUSTOMER_NAME,
    PRICE,
    STATUS,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        if (this == EMPLOYEE_NAME)
            return "employees.name";
        if (this == CUSTOMER_NAME)
            return "customers.name";
        if (this == CUSTOMER)
            return "o.customer";

        return "offers." + this.name().toLowerCase();
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
