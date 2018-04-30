package tvestergaard.fog.logic.roofings;

import java.util.Collections;
import java.util.Set;

public class RoofingValidatorException extends Exception
{
    /**
     * The reasons for throwing the exception.
     */
    private Set<RoofingError> errors;

    /**
     * Creates a new {@link RoofingValidatorException}.
     *
     * @param errors The reasons for throwing the exception.
     */
    public RoofingValidatorException(Set<RoofingError> errors)
    {
        this.errors = errors;
    }

    /**
     * Checks if the provided reason was a cause of the exception.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was a cause of the exception.
     */
    public boolean hasError(RoofingError reason)
    {
        return this.errors.contains(reason);
    }

    /**
     * Returns the errors that caused the exception to be thrown.
     *
     * @return The errors that caused the exception to be thrown.
     */
    public Set<RoofingError> getErrors()
    {
        return Collections.unmodifiableSet(errors);
    }
}
