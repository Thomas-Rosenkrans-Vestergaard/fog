package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.contraints.Constraint;

import java.util.List;

public interface CladdingDAO
{

    /**
     * Returns the {@link Cladding}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The complete list of the {@link Cladding}s in the system.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Cladding> get(Constraint... constraints) throws DataAccessException;
}
