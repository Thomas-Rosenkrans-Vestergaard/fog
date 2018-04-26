package tvestergaard.fog.logic.claddings;

import java.util.Set;

public class CladdingValidatorException extends Exception
{

    /**
     * The reasons for throwing the exception.
     */
    private Set<CladdingError> reasons;

    /**
     * Creates a new {@link CladdingValidatorException}.
     *
     * @param reasons The reasons for throwing the exception.
     */
    public CladdingValidatorException(Set<CladdingError> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Checks if the provided reason was a cause of the exception.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was a cause of the exception.
     */
    public boolean hasReason(CladdingError reason)
    {
        return this.reasons.contains(reason);
    }
}
