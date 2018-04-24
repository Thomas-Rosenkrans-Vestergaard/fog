package tvestergaard.fog.data.sheds;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface ShedDAO
{

    /**
     * Returns the {@link Shed}s in the data storage.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Shed}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Shed> get(Constraint... constraints) throws DataAccessException;
}
