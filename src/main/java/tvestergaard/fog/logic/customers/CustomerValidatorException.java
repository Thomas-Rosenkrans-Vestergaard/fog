package tvestergaard.fog.logic.customers;

import java.util.HashSet;
import java.util.Set;

public class CustomerValidatorException extends Exception
{

    /**
     * The reasons for throwing the {@link CustomerValidatorException}.
     */
    private final Set<CustomerError> reasons;

    /**
     * Creates a new {@link CustomerValidatorException}.
     *
     * @param reasons The reasons for throwing the {@link CustomerValidatorException}.
     */
    public CustomerValidatorException(Set<CustomerError> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link CustomerValidatorException}.
     *
     * @param reason The reason for throwing the {@link CustomerValidatorException}.
     */
    public CustomerValidatorException(CustomerError reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link CustomerValidatorException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link CustomerValidatorException}.
     */
    public boolean isReason(CustomerError reason)
    {
        return reasons.contains(reason);
    }

    /**
     * Returns the reasons for throwing the {@link CustomerValidatorException}.
     *
     * @return The reasons for throwing the {@link CustomerValidatorException}.
     */
    public Set<CustomerError> getReasons()
    {
        return this.reasons;
    }
}
