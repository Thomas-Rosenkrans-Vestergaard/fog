package tvestergaard.fog.data.employees;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class EmployeeRecord implements Employee
{

    /**
     * The unique identifier of the employee.
     */
    private final int id;

    /**
     * The name of the employee.
     */
    private String name;

    /**
     * The username of the employee.
     */
    private String username;

    /**
     * The hashed password of the employee.
     */
    private String password;

    /**
     * Whether or not the employee is currently active.
     */
    private boolean active;

    /**
     * The roles assigned to the employee.
     */
    private final Set<Role> roles;

    /**
     * The {@code LocalDataTime} representing the moment when the employee was created.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new {@link EmployeeRecord}.
     *
     * @param id        The unique identifier of the employee.
     * @param name      The name of the employee.
     * @param username  The username of the employee.
     * @param password  The hashed password of the employee.
     * @param active    Whether or not the employee is currently active.
     * @param roles     The roles assigned to the employee.
     * @param createdAt The {@code LocalDataTime} representing the moment when the employee was created.
     */
    public EmployeeRecord(int id, String name, String username, String password, boolean active, Set<Role> roles, LocalDateTime createdAt)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the employee.
     *
     * @return The unique identifier of the employee.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the employee.
     *
     * @return The name of the employee.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name The new name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the username of the employee.
     *
     * @return The username of the employee.
     */
    @Override public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username of the employee.
     *
     * @param username The new username.
     */
    @Override public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Returns the password of the employee.
     *
     * @return The password of the employee.
     */
    @Override public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password of the employee.
     *
     * @param password The new password.
     */
    @Override public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns {@code true} if the employee is active within the employee.
     *
     * @return {@code true} if the employee is active within the employee.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the activity status of the employee is active within the employee.
     *
     * @param active The activity status.
     */
    @Override public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Checks if the employee is of the provided role.
     *
     * @param role The role.
     * @return {@code true} if the employee is of the provided role.
     */
    @Override public boolean is(Role role)
    {
        return roles.contains(role);
    }

    /**
     * Removes the provided role from the employee.
     *
     * @param role The role to remove.
     * @return {@code true} if the role was removed.
     */
    @Override public boolean remove(Role role)
    {
        return roles.remove(role);
    }

    /**
     * Returns the set containing all the roles assigned to the employee.
     *
     * @return The set containing all the roles assigned to the employee.
     */
    @Override public Set<Role> getRoles()
    {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Returns the {@code LocalDataTime} representing the moment when the employee was created.
     *
     * @return The {@code LocalDataTime} representing the moment when the employee was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee that = (Employee) o;
        return getId() == that.getId() &&
                isActive() == that.isActive() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getRoles(), that.getRoles()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
