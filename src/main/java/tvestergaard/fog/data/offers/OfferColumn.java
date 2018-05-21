package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OfferColumn implements Column<OfferColumn>, MysqlColumn
{

    ID,
    ORDER,
    EMPLOYEE,
    PRICE,
    STATUS,
    CREATED_AT,
    SEARCH;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return "offers." + this.name().toLowerCase();
    }

    /**
     * Returns the column, that should be used in ORDER BY clauses. Used to order when using foreign keys.
     *
     * @return The column, that should be used in ORDER BY clauses.
     */
    @Override public String getForeignColumn()
    {
        if(this == ORDER)
            return "o.id";

        if(this == EMPLOYEE)
            return "employees.name";

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
