package tvestergaard.fog.data.employees;

import tvestergaard.fog.data.constraints.Column;
import tvestergaard.fog.data.constraints.MysqlColumn;

public enum EmployeeColumn implements Column<EmployeeColumn>, MysqlColumn
{
    ID,
    NAME,
    USERNAME,
    PASSWORD,
    ACTIVE,
    CREATED_AT;

    /**
     * Returns the name of the column in MySQL.
     *
     * @return The name of the column in MySQL.
     */
    @Override public String getMysqlName()
    {
        return this.name().toLowerCase();
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
