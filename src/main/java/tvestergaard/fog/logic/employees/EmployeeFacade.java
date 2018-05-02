package tvestergaard.fog.logic.employees;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.employees.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.employees.EmployeeColumn.ID;
import static tvestergaard.fog.data.employees.EmployeeColumn.USERNAME;

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
     * Creates a new {@link EmployeeFacade} using a {@link MysqlEmployeeDAO} with the connection provided from {@link
     * ProductionDataSource#getSource()}.
     */
    public EmployeeFacade()
    {
        MysqlDataSource source = ProductionDataSource.getSource();
        this.employeeDAO = new MysqlEmployeeDAO(source);
        this.validator = new EmployeeValidator(this.employeeDAO);
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
     * @param roles    The roles the employee has.
     * @param active   Whether or not the employee can be applied to orders.
     * @return The employee instance representing the newly created employee.
     * @throws EmployeeValidatorException When the provided details are invalid.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     */
    public Employee create(String name, String username, String password, int[] roles, boolean active)
            throws EmployeeValidatorException
    {
        try {
            Set<EmployeeError> reasons = validator.validateRegister(name, username, password);
            if (!reasons.isEmpty())
                throw new EmployeeValidatorException(reasons);
            EmployeeBlueprint blueprint = EmployeeBlueprint.from(name, username, password, createRoleSet(roles), active);
            blueprint.setPassword(hash(password));
            Employee employee = employeeDAO.create(blueprint);
            return employee;
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Attempts to authenticate a employee using the provided email and password.
     *
     * @param username The email to authenticate with.
     * @param password The password to authenticate with.
     * @return The employee who was authenticated. {@code null} in case no employee with the provided credentials exist..
     */
    public Employee authenticate(String username, String password)
    {
        try {
            Employee employee = employeeDAO.first(where(eq(USERNAME, username)));
            if (employee == null)
                return null;

            return BCrypt.checkpw(password, employee.getPassword()) ? employee : null;
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
            int[] roles,
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
            EmployeeUpdater updater = EmployeeUpdater.from(id, name, username, employee.getPassword(), createRoleSet(roles), active);
            if (password != null)
                updater.setPassword(hash(password));
            return employeeDAO.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    private Set<Role> createRoleSet(int[] roles)
    {
        Set<Role> result = new HashSet<>();
        for (int role : roles) {
            result.add(Role.from(role));
        }

        return result;
    }
}
