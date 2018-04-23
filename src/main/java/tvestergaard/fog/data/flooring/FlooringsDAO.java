package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface FlooringsDAO
{

    /**
     * Returns a complete list of the {@link Flooring}s in the system.
     *
     * @return The complete list of the {@link Flooring}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    public List<Flooring> getAll() throws DataAccessException;
}
