package tvestergaard.fog.data.customers;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum CustomerColumn implements MysqlColumn
{
    ID("id"),
    NAME("name"),
    ADDRESS("address"),
    EMAIL("email"),
    PHONE("phone"),
    PASSWORD("password"),
    CONTACT_METHOD("contact_method"),
    ACTIVE("active"),
    CREATED_AT("created_at");

    /**
     * The name of the column in mysql.
     */
    private final String mysqlName;

    /**
     * Creates a new {@link CustomerColumn}.
     *
     * @param mysqlName The name of the column in mysql.
     */
    CustomerColumn(String mysqlName)
    {
        this.mysqlName = mysqlName;
    }

    /**
     * Returns the name of the column in mysql.
     *
     * @return The name of the column in mysql.
     */
    @Override public String getMysqlName()
    {
        return mysqlName;
    }
}
