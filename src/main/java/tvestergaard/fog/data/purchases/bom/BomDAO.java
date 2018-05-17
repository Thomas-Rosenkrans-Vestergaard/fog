package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.data.DataAccessException;

public interface BomDAO
{

    /**
     * Returns the bom with the provided id.
     *
     * @param id The id of the bom to retrieve.
     * @return The bom instance representing the retrieved bom. {@code null} in case no bom with the provided id exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Bom get(int id) throws DataAccessException;
}
