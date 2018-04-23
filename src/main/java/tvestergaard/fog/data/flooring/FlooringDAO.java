package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface FlooringDAO
{

    /**
     * Returns a complete list of the {@link Flooring}s in the system.
     *
     * @return The complete list of the {@link Flooring}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Flooring> get() throws DataAccessException;
}
