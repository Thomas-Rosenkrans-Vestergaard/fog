package tvestergaard.fog.logic.floorings;

import java.util.Set;

public class FlooringValidatorException extends Exception
{

    /**
     * The reasons for throwing the exception.
     */
    private Set<FlooringError> reasons;

    /**
     * Creates a new {@link FlooringValidatorException}.
     *
     * @param reasons The reasons for throwing the exception.
     */
    public FlooringValidatorException(Set<FlooringError> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Checks if the provided reason was a cause of the exception.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was a cause of the exception.
     */
    public boolean hasReason(FlooringError reason)
    {
        return this.reasons.contains(reason);
    }
}
