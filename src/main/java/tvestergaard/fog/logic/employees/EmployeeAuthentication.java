package tvestergaard.fog.logic.employees;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeBlueprint;
import tvestergaard.fog.data.employees.EmployeeDAO;
import tvestergaard.fog.data.employees.Role;

import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.employees.EmployeeColumn.USERNAME;

public class EmployeeAuthentication
{

    private final EmployeeDAO       employeeDAO;
    private final EmployeeValidator validator;

    public EmployeeAuthentication(EmployeeDAO employeeDAO, EmployeeValidator validator)
    {
        this.employeeDAO = employeeDAO;
        this.validator = validator;
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
    public Employee register(String name,
                             String username,
                             String password,
                             Set<Role> roles,
                             boolean active) throws DataAccessException, EmployeeValidatorException
    {
        Set<EmployeeError> reasons = validator.validateRegister(name, username, password);
        if (!reasons.isEmpty())
            throw new EmployeeValidatorException(reasons);
        EmployeeBlueprint blueprint = EmployeeBlueprint.from(name, username, password, roles, active);
        blueprint.setPassword(hash(password));
        return employeeDAO.create(blueprint);
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
    public Employee authenticate(String username, String password) throws DataAccessException, IncorrectCredentialsException, InactiveEmployeeException
    {
        Employee employee = employeeDAO.first(where(eq(USERNAME, username)));
        if (employee == null)
            return null;

        if (!employee.isActive())
            throw new InactiveEmployeeException();

        if (!BCrypt.checkpw(password, employee.getPassword()))
            throw new IncorrectCredentialsException();

        return employee;
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
}
