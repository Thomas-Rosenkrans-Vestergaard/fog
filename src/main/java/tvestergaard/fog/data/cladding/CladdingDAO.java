package tvestergaard.fog.data.cladding;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;

import java.util.List;

import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;

public interface CladdingDAO
{

    /**
     * Returns the claddings in the data storage.
     *
     * @return The complete list of the claddings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default List<Cladding> get() throws DataAccessException
    {
        return get(new Constraints<>());
    }

    /**
     * Returns the cladding with the provided id.
     *
     * @param id The id of the cladding to return.
     * @return The cladding with the provided id. Returns {@code null} if no such cladding exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Cladding get(int id) throws DataAccessException
    {
        return first(where(eq(ID, id)));
    }

    /**
     * Returns the claddings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The list of the claddings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Cladding> get(Constraints<CladdingColumn> constraints) throws DataAccessException;

    /**
     * Returns the first cladding matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first cladding matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Cladding first(Constraints<CladdingColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new cladding into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The cladding instance representing the newly created cladding.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Cladding create(CladdingBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(CladdingUpdater updater) throws DataAccessException;
}
