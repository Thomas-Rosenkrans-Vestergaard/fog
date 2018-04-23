package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface CladdingDAO
{

    /**
     * Returns a complete list of the {@link Cladding}s in the system.
     *
     * @return The complete list of the {@link Cladding}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Cladding> get() throws DataAccessException;
}
