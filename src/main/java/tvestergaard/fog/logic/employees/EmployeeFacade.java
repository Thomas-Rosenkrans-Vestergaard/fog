package tvestergaard.fog.logic.employees;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.employees.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.employees.EmployeeColumn.ID;

public class EmployeeFacade
{

    /**
     * The {@link EmployeeDAO} used to access and make changes to the data storage used by the application.
     */
    private final EmployeeDAO employeeDAO;

    /**
     * The validator responsible for validating information about employees.
     */
    private final EmployeeValidator validator;

    private final EmployeeAuthentication authentication;

    /**
     * Creates a new {@link EmployeeFacade}.
     *
     * @param employeeDAO The {@link EmployeeDAO} used to access and make changes to the data storage used by the
     *                    application.
     */
    public EmployeeFacade(EmployeeDAO employeeDAO)
    {
        this.employeeDAO = employeeDAO;
        this.validator = new EmployeeValidator(employeeDAO);
        this.authentication = new EmployeeAuthentication(employeeDAO, validator);
    }

    /**
     * Returns the employees in the application. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting employees.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Employee> get(Constraint<EmployeeColumn>... constraints)
    {
        try {
            return employeeDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first employee matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first employee matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Employee first(Constraint<EmployeeColumn>... constraints)
    {
        try {
            return employeeDAO.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new employee into the application.
     *
     * @param name     The name of the employee to create.
     * @param username The username of the employee to create.
     * @param password The password of the employee to create.
     * @param roles    The roles to assign to the employee.
     * @param active   Whether or not the employee can be applied to orders.
     * @return The employee instance representing the newly created employee.
     * @throws DataAccessException        When a data storage exception occurs.
     * @throws EmployeeValidatorException When the provided details are invalid.
     */
    public Employee register(String name, String username, String password, Set<Role> roles, boolean active) throws EmployeeValidatorException
    {
        try {
            return authentication.register(name, username, password, roles, active);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Attempts to authenticate a employee using the provided email and password.
     *
     * @param username The email to authenticate with.
     * @param password The password to authenticate with.
     * @return The employee who was authenticated. {@code null} in case no employee with the provided credentials exist.
     * @throws DataAccessException       When a data storage exception occurs.
     * @throws InactiveEmployeeException When the provided employee is marked inactive.
     */
    public Employee authenticate(String username, String password) throws InactiveEmployeeException
    {
        try {
            return authentication.authenticate(username, password);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Hashes the provided password using the b-crypt algorithm.
     *
     * @param password The password to hash.
     * @return The resulting digest.
     */
    public static String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Updates the entity in the application to match the provided {@code employee}.
     *
     * @param id       The id of the employee to update.
     * @param name     The new name.
     * @param username The new username.
     * @param password The new password.
     * @param roles    The roles to assign to the employee.
     * @param active   The new active status.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws EmployeeValidatorException When the provided employee information is considered invalid.
     */
    public boolean update(
            int id,
            String name,
            String username,
            Set<Role> roles,
            String password,
            boolean active) throws UnknownEmployeeException, EmployeeValidatorException
    {
        try {
            Set<EmployeeError> reasons = validator.validateUpdate(id, name, username, password);
            if (!reasons.isEmpty()) {
                throw new EmployeeValidatorException(reasons);
            }
            Employee employee = employeeDAO.first(where(eq(ID, id)));
            if (employee == null)
                throw new UnknownEmployeeException();
            EmployeeUpdater updater = EmployeeUpdater.from(id, name, username, employee.getPassword(), roles, active);
            if (password != null)
                updater.setPassword(hash(password));
            return employeeDAO.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
