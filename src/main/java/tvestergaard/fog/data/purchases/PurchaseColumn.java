package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum PurchaseColumn implements Column<PurchaseColumn>, MysqlColumn
{
    ID,
    OFFER,
    EMPLOYEE,
    BOM,
    CREATED_AT,
    SEARCH;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return String.format("purchases.%s", this.name().toLowerCase());
    }

    /**
     * Returns the column, that should be used in ORDER BY clauses. Used to order when using foreign keys.
     *
     * @return The column, that should be used in ORDER BY clauses.
     */
    @Override public String getOrderColumn()
    {
        if (this == OFFER)
            return "offers.id";

        if (this == EMPLOYEE)
            return "p_emp.id";

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
