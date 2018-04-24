package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface RoofingDAO
{

    /**
     * Returns a complete list of the {@link Roofing}s in the system.
     *
     * @return The complete list of the {@link Roofing}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Roofing> get() throws DataAccessException;
}
