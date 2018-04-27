package tvestergaard.fog.data.employees;

import java.time.LocalDateTime;
import java.util.Set;

public interface EmployeeUpdater extends EmployeeBlueprint
{


    /**
     * Returns the unique identifier of the employee.
     *
     * @return The unique identifier of the employee.
     */
    int getId();

    /**
     * Returns the {@code LocalDataTime} representing the moment when the employee was created.
     *
     * @return The {@code LocalDataTime} representing the moment when the employee was created.
     */
    LocalDateTime getCreatedAt();
}
