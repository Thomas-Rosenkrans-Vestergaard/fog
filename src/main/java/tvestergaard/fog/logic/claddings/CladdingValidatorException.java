package tvestergaard.fog.logic.claddings;

import java.util.Set;

public class CladdingValidatorException extends Exception
{

    /**
     * The errors for throwing the exception.
     */
    private Set<CladdingError> errors;

    /**
     * Creates a new {@link CladdingValidatorException}.
     *
     * @param errors The errors for throwing the exception.
     */
    public CladdingValidatorException(Set<CladdingError> errors)
    {
        this.errors = errors;
    }

    /**
     * Checks if the provided error was a cause of the exception.
     *
     * @param error The error to check for.
     * @return {@code true} if the provided error was a cause of the exception.
     */
    public boolean hasError(CladdingError error)
    {
        return this.errors.contains(error);
    }

    public Set<CladdingError> getErrors()
    {
        return this.errors;
    }
}
