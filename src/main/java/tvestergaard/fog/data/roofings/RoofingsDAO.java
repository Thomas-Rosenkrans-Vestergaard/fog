package tvestergaard.fog.data.roofings;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface RoofingsDAO
{

    /**
     * Returns a complete list of the {@link Roofing}s in the system.
     *
     * @return The complete list of the {@link Roofing}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    public List<Roofing> getAll() throws DataAccessException;
}
