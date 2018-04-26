package tvestergaard.fog.logic.roofings;

import java.util.Set;

public class RoofingValidatorException extends Exception
{
    /**
     * The reasons for throwing the exception.
     */
    private Set<RoofingError> reasons;

    /**
     * Creates a new {@link RoofingValidatorException}.
     *
     * @param reasons The reasons for throwing the exception.
     */
    public RoofingValidatorException(Set<RoofingError> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Checks if the provided reason was a cause of the exception.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was a cause of the exception.
     */
    public boolean hasReason(RoofingError reason)
    {
        return this.reasons.contains(reason);
    }
}
