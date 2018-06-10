package tvestergaard.fog.data.employees;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.employees.EmployeeColumn.ID;
import static tvestergaard.fog.data.employees.EmployeeColumn.USERNAME;

public interface EmployeeDAO
{

    /**
     * Returns the employees in the data storage.
     *
     * @return The resulting employees.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default List<Employee> get() throws DataAccessException
    {
        return get(new Constraints<>());
    }

    /**
     * Returns the employee with the provided id.
     *
     * @param id The id of the employee to return.
     * @return The employee with the provided id. Returns {@code null} if no such employee exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Employee get(int id) throws DataAccessException
    {
        return first(where(eq(ID, id)));
    }

    /**
     * Returns the employee with the provided username.
     *
     * @param username The username of the employee to return.
     * @return The employee with the provided username. Returns {@code null} if no such employee exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Employee get(String username) throws DataAccessException
    {
        return first(where(eq(USERNAME, username)));
    }

    /**
     * Returns the employees in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting employees.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Employee> get(Constraints<EmployeeColumn> constraints) throws DataAccessException;

    /**
     * Returns the first employee matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first employee matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Employee first(Constraints<EmployeeColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new employee into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The employee instance representing the newly created employee.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Employee create(EmployeeBlueprint blueprint) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code employee}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(EmployeeUpdater updater) throws DataAccessException;
}
