package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.contraints.Constraint;

import java.util.List;

public interface RoofingDAO
{

    /**
     * Returns the {@link Roofing}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Roofing}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    List<Roofing> get(Constraint... constraints) throws DataAccessException;
}
