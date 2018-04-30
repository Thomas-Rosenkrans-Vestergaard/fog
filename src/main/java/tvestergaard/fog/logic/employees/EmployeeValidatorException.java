package tvestergaard.fog.logic.employees;

import java.util.HashSet;
import java.util.Set;

public class EmployeeValidatorException extends Exception
{

    /**
     * The errors for throwing the {@link EmployeeValidatorException}.
     */
    private final Set<EmployeeError> errors;

    /**
     * Creates a new {@link EmployeeValidatorException}.
     *
     * @param errors The errors for throwing the {@link EmployeeValidatorException}.
     */
    public EmployeeValidatorException(Set<EmployeeError> errors)
    {
        this.errors = errors;
    }

    /**
     * Creates a new {@link EmployeeValidatorException}.
     *
     * @param reason The reason for throwing the {@link EmployeeValidatorException}.
     */
    public EmployeeValidatorException(EmployeeError reason)
    {
        this.errors = new HashSet<>();
        this.errors.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link EmployeeValidatorException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link EmployeeValidatorException}.
     */
    public boolean hasError(EmployeeError reason)
    {
        return errors.contains(reason);
    }

    /**
     * Returns the errors for throwing the {@link EmployeeValidatorException}.
     *
     * @return The errors for throwing the {@link EmployeeValidatorException}.
     */
    public Set<EmployeeError> getErrors()
    {
        return this.errors;
    }
}
