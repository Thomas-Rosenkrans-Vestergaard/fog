package tvestergaard.fog.data.sheds;

import tvestergaard.fog.data.DataAccessException;

import java.util.List;

public interface ShedDAO
{

    /**
     * Returns a complete list of the {@link Shed}s in the system.
     *
     * @return The complete list of the {@link Shed}s.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    List<Shed> get() throws DataAccessException;
}
