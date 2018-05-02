package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.constraints.MysqlColumn;

public enum OfferColumn implements MysqlColumn
{

    ID,
    ORDER,
    EMPLOYEE,
    PRICE,
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
}
