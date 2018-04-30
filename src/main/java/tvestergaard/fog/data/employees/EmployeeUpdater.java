package tvestergaard.fog.data.employees;

import java.util.Set;

public interface EmployeeUpdater extends EmployeeBlueprint
{


    /**
     * Returns a new employee updater from the provided information.
     *
     * @param id       The id of the employee to specify in the updater.
     * @param name     The name of the employee to specify in the updater.
     * @param username The username of the employee to specify in the updater.
     * @param password The password of the employee to specify in the updater.
     * @param roles    The roles of the employee to specify in the updater.
     * @param active   Whether or not the employee to specify in the updater is active.
     * @return The newly created employee updater.
     */
    static EmployeeUpdater from(int id, String name, String username, String password, Set<Role> roles, boolean active)
    {
        return new EmployeeRecord(id, name, username, password, active, roles, null);
    }

    /**
     * Returns the unique identifier of the employee.
     *
     * @return The unique identifier of the employee.
     */
    int getId();
}
