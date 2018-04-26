package tvestergaard.fog.logic;

import java.util.HashSet;
import java.util.Set;

public class CustomerValidationException extends Exception
{

    /**
     * The possible reasons for throwing the {@link CustomerValidationException}.
     */
    public enum Reason
    {
        NAME_EMPTY,
        NAME_LONGER_THAN_255,
        ADDRESS_EMPTY,
        ADDRESS_LONGER_THAN_255,
        EMAIL_INVALID,
        EMAIL_LONGER_THAN_255,
        EMAIL_TAKEN,
        PHONE_EMPTY,
        PHONE_LONGER_THAN_30,
        PASSWORD_SHORTER_THAN_4,
        UNKNOWN_CONTACT_METHOD
    }

    /**
     * The reasons for throwing the {@link CustomerValidationException}.
     */
    private final Set<Reason> reasons;

    /**
     * Creates a new {@link CustomerValidationException}.
     *
     * @param reasons The reasons for throwing the {@link CustomerValidationException}.
     */
    public CustomerValidationException(Set<Reason> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link CustomerValidationException}.
     *
     * @param reason The reason for throwing the {@link CustomerValidationException}.
     */
    public CustomerValidationException(Reason reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link CustomerValidationException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link CustomerValidationException}.
     */
    public boolean isReason(Reason reason)
    {
        return reasons.contains(reason);
    }

    /**
     * Returns the reasons for throwing the {@link CustomerValidationException}.
     *
     * @return The reasons for throwing the {@link CustomerValidationException}.
     */
    public Set<Reason> getReasons()
    {
        return this.reasons;
    }
}
