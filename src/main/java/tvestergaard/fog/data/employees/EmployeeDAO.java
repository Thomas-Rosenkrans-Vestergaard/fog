package tvestergaard.fog.data.employees;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface EmployeeDAO
{

    /**
     * Returns the employees in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting employees.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Employee> get(Constraint<EmployeeColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first employee matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first employee matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Employee first(Constraint<EmployeeColumn>... constraints) throws DataAccessException;

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
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(EmployeeUpdater updater) throws DataAccessException;

    /**
     * Returns the number of employees in the data storage.
     *
     * @return The number of employees in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int size() throws DataAccessException;
}
