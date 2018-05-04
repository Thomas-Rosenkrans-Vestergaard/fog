package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OfferColumn implements MysqlColumn
{

    ID,
    ORDER,
    EMPLOYEE,
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
