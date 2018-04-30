package tvestergaard.fog.logic.floorings;

import java.util.Set;

public class FlooringValidatorException extends Exception
{

    /**
     * The errors that caused the {@link FlooringValidatorException}.
     */
    private Set<FlooringError> errors;

    /**
     * Creates a new {@link FlooringValidatorException}.
     *
     * @param errors The errors that caused the {@link FlooringValidatorException}.
     */
    public FlooringValidatorException(Set<FlooringError> errors)
    {
        this.errors = errors;
    }

    /**
     * Returns the errors that caused the {@link FlooringValidatorException}.
     *
     * @return The errors that caused the {@link FlooringValidatorException}.
     */
    public Set<FlooringError> getErrors()
    {
        return this.errors;
    }
}
