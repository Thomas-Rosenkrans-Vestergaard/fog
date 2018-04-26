package tvestergaard.fog.logic;

import java.util.HashSet;
import java.util.Set;

public class OrderValidationException extends Exception
{

    /**
     * The possible reasons for throwing the {@link OrderValidationException}.
     */
    public enum Reason
    {
        INCOMPLETE_ORDER,
        UNKNOWN_CUSTOMER,
        INACTIVE_CUSTOMER,
        UNKNOWN_CLADDING,
        INACTIVE_CLADDING,
        ILLEGAL_WIDTH,
        ILLEGAL_LENGTH,
        ILLEGAL_HEIGHT,
        UNKNOWN_ROOFING,
        INACTIVE_ROOFING,
        ILLEGAL_SLOPE,
        ILLEGAL_SHED_WIDTH,
        ILLEGAL_SHED_DEPTH,
        UNKNOWN_SHED_CLADDING,
        INACTIVE_SHED_CLADDING,
        UNKNOWN_SHED_FLOORING,
        INACTIVE_SHED_FLOORING
    }

    /**
     * The reasons for throwing the {@link OrderValidationException}.
     */
    private final Set<OrderValidationException.Reason> reasons;

    /**
     * Creates a new {@link OrderValidationException}.
     *
     * @param reasons The reasons for throwing the {@link OrderValidationException}.
     */
    public OrderValidationException(Set<OrderValidationException.Reason> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link OrderValidationException}.
     *
     * @param reason The reason for throwing the {@link OrderValidationException}.
     */
    public OrderValidationException(Reason reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link OrderValidationException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link OrderValidationException}.
     */
    public boolean isReason(Reason reason)
    {
        return reasons.contains(reason);
    }

    /**
     * Returns the reasons for throwing the {@link OrderValidationException}.
     *
     * @return The reasons for throwing the {@link OrderValidationException}.
     */
    public Set<OrderValidationException.Reason> getReasons()
    {
        return this.reasons;
    }
}
