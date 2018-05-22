package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum PurchaseColumn implements Column<PurchaseColumn>, MysqlColumn<PurchaseColumn>
{
    ID,
    OFFER,
    EMPLOYEE,
    EMPLOYEE_NAME,
    CUSTOMER,
    CUSTOMER_NAME,
    PURCHASE_PRICE,
    BOM,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        if (this == EMPLOYEE)
            return "employees.id";
        if (this == EMPLOYEE_NAME)
            return "employees.name";
        if (this == CUSTOMER)
            return "customers.id";
        if (this == CUSTOMER_NAME)
            return "customers.name";
        if (this == PURCHASE_PRICE)
            return "offers.price";

        return String.format("purchases.%s", this.name().toLowerCase());
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
