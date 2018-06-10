package tvestergaard.fog.logic.employees;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.employees.EmployeeDAO;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.employees.EmployeeColumn.ID;
import static tvestergaard.fog.data.employees.EmployeeColumn.USERNAME;
import static tvestergaard.fog.logic.employees.EmployeeError.*;

public class EmployeeValidator
{

    /**
     * The {@link EmployeeDAO} used to access the employee storage of the application.
     */
    private final EmployeeDAO employeeDAO;

    /**
     * Creates a new {@link EmployeeValidator}.
     *
     * @param employeeDAO The {@link EmployeeDAO} used to access the employee storage of the application.
     */
    public EmployeeValidator(EmployeeDAO employeeDAO)
    {
        this.employeeDAO = employeeDAO;
    }

    /**
     * Validates the provided employee blueprint.
     *
     * @param name     The name of the employee to validate.
     * @param username The username of the employee to validate.
     * @param password The password of the employee to validate.
     * @return The errors with the provided information.
     * @throws DataAccessException When a data access exception occurs.
     * @see EmployeeFacade#register(String, String, String, Set, boolean)
     */
    public Set<EmployeeError> validateRegister(String name, String username, String password) throws DataAccessException
    {
        Set<EmployeeError> errors = validateInformation(name, username, password);
        if (employeeDAO.get(username) != null)
            errors.add(USERNAME_TAKEN);

        return errors;
    }

    /**
     * Validates the provided employee updater.
     *
     * @param id       The id of the employee to validate.
     * @param name     The name of the employee to validate.
     * @param username The username of the employee to validate.
     * @param password The password of the employee to validate.
     * @return The errors with the provided information.
     * @throws DataAccessException When a data access exception occurs.
     * @see EmployeeFacade#update(int, String, String, Set, String, boolean)
     */
    public Set<EmployeeError> validateUpdate(int id, String name, String username, String password) throws DataAccessException
    {
        Set<EmployeeError> errors = validateInformation(name, username, password);
        if (employeeDAO.first(where(eq(USERNAME, username), and(not(ID, id)))) != null)
            errors.add(USERNAME_TAKEN);

        return errors;
    }

    /**
     * Validates the provided information for creation.
     *
     * @param name     The name of the employee to validate.
     * @param username The username of the employee to validate.
     * @param password The password of the employee to validate.
     * @return The errors with the provided information.
     */
    private Set<EmployeeError> validateInformation(String name, String username, String password)
    {
        Set<EmployeeError> reasons = new HashSet<>();

        if (name == null || name.isEmpty())
            reasons.add(NAME_EMPTY);
        else if (name.length() > 255)
            reasons.add(NAME_LONGER_THAN_255);

        if (username == null || username.isEmpty())
            reasons.add(USERNAME_EMPTY);
        else if (username.length() > 255)
            reasons.add(USERNAME_LONGER_THAN_255);

        if (password != null && password.length() < 4)
            reasons.add(PASSWORD_SHORTER_THAN_4);

        return reasons;
    }
}
