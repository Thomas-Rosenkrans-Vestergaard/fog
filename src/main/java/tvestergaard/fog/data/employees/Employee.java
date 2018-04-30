package tvestergaard.fog.data.employees;

import java.time.LocalDateTime;
import java.util.Set;

public interface Employee extends EmployeeUpdater
{

    /**
     * Returns the {@code LocalDataTime} representing the moment when the employee was created.
     *
     * @return The {@code LocalDataTime} representing the moment when the employee was created.
     */
    LocalDateTime getCreatedAt();

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
