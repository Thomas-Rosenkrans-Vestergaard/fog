package tvestergaard.fog.logic.orders;

import java.util.HashSet;
import java.util.Set;

public class OrderValidatorException extends Exception
{

    /**
     * The reasons for throwing the {@link OrderValidatorException}.
     */
    private final Set<OrderError> reasons;

    /**
     * Creates a new {@link OrderValidatorException}.
     *
     * @param reasons The reasons for throwing the {@link OrderValidatorException}.
     */
    public OrderValidatorException(Set<OrderError> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link OrderValidatorException}.
     *
     * @param reason The reason for throwing the {@link OrderValidatorException}.
     */
    public OrderValidatorException(OrderError reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link OrderValidatorException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link OrderValidatorException}.
     */
    public boolean isReason(OrderError reason)
    {
        return reasons.contains(reason);
    }

    /**
     * Returns the reasons for throwing the {@link OrderValidatorException}.
     *
     * @return The reasons for throwing the {@link OrderValidatorException}.
     */
    public Set<OrderError> getReasons()
    {
        return this.reasons;
    }
}
