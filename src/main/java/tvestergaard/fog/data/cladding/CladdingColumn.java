package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum CladdingColumn implements MysqlColumn
{

    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE_PER_METER("price_per_square_meter"),
    ACTIVE("active");

    /**
     * The name of the column in SQL.
     */
    private final String SQLName;

    /**
     * Creates a new {@link CladdingColumn}.
     *
     * @param SQLName The name of the column in SQL.
     */
    CladdingColumn(String SQLName)
    {
        this.SQLName = SQLName;
    }

    /**
     * Returns the name of the column in SQL.
     *
     * @return The name of the column in SQL.
     */
    @Override
    public String getSQLName()
    {
        return SQLName;
    }
}
