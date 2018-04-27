package tvestergaard.fog.data.employees;

import java.util.Set;

public interface EmployeeBlueprint
{

    /**
     * Returns the name of the employee.
     *
     * @return The name of the employee.
     */
    String getName();

    /**
     * Sets the name of the employee.
     *
     * @param name The new name.
     */
    void setName(String name);

    /**
     * Returns the username of the employee.
     *
     * @return The username of the employee.
     */
    String getUsername();

    /**
     * Sets the username of the employee.
     *
     * @param username The new username.
     */
    void setUsername(String username);

    /**
     * Returns the password of the employee.
     *
     * @return The password of the employee.
     */
    String getPassword();

    /**
     * Sets the password of the employee.
     *
     * @param password The new password.
     */
    void setPassword(String password);

    /**
     * Returns {@code true} if the employee is active within the employee.
     *
     * @return {@code true} if the employee is active within the employee.
     */
    boolean isActive();

    /**
     * Sets the activity status of the employee is active within the employee.
     *
     * @param active The activity status.
     */
    void setActive(boolean active);

    /**
     * Checks if the employee is of the provided role.
     *
     * @param role The role.
     * @return {@code true} if the employee is of the provided role.
     */
    boolean is(Role role);

    /**
     * Removes the provided role from the employee.
     *
     * @param role The role to remove.
     * @return {@code true} if the role was removed.
     */
    boolean remove(Role role);

    /**
     * Returns the set containing all the roles assigned to the employee.
     *
     * @return The set containing all the roles assigned to the employee.
     */
    Set<Role> getRoles();
}
