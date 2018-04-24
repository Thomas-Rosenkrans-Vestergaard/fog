package tvestergaard.fog.data.flooring;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.contraints.Constraint;

import java.util.List;

public interface FlooringDAO
{

    /**
     * Returns the {@link Flooring}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Flooring}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Flooring> get(Constraint... constraints) throws DataAccessException;
}
