package tvestergaard.fog.data.employees;

import java.util.Set;

public interface Employee extends EmployeeUpdater
{

    /**
     * Returns a new employee blueprint from the provided information.
     *
     * @param name     The name of the employee to specify in the blueprint.
     * @param username The username of the employee to specify in the blueprint.
     * @param password The password of the employee to specify in the blueprint.
     * @param roles    The roles of the employee to specify in the blueprint.
     * @param active   Whether or not the employee to specify in the blueprint is active.
     * @return The newly created employee blueprint.
     */
    static EmployeeBlueprint blueprint(String name, String username, String password, Set<Role> roles, boolean active)
    {
        return new EmployeeRecord(-1, name, username, password, active, roles, null);
    }

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
    static EmployeeUpdater updater(int id, String name, String username, String password, Set<Role> roles, boolean active)
    {
        return new EmployeeRecord(id, name, username, password, active, roles, null);
    }

    /**
     * Checks that this employee equals another provided object. The two objects are only considered equal when all the
     * attributes of the two employees are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the employee.
     *
     * @return The id of the employee.
     */
    int hashCode();
}
