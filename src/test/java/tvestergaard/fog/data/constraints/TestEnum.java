package tvestergaard.fog.data.constraints;

public enum TestEnum implements MysqlColumn
{
    COLUMN_ONE("column_one"),
    COLUMN_TWO("column_two"),
    COLUMN_THREE("column_three");

    private final String mysqlColumn;

    TestEnum(String mysqlColumn)
    {
        this.mysqlColumn = mysqlColumn;
    }


    /**
     * Returns the name of the column in mysql.
     *
     * @return The name of the column in mysql.
     */
    @Override public String getMysqlName()
    {
        return mysqlColumn;
    }
}
