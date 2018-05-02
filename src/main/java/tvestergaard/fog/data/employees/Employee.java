package tvestergaard.fog.data.employees;

import java.time.LocalDateTime;

public interface Employee extends EmployeeUpdater
{

    /**
     * Returns the {@code LocalDataTime} representing the moment when the employee was created.
     *
     * @return The {@code LocalDataTime} representing the moment when the employee was created.
     */
    LocalDateTime getCreatedAt();
}
