package tvestergaard.fog.logic.customers;

import java.util.HashSet;
import java.util.Set;

public class CustomerValidatorException extends Exception
{

    /**
     * The errors for throwing the {@link CustomerValidatorException}.
     */
    private final Set<CustomerError> errors;

    /**
     * Creates a new {@link CustomerValidatorException}.
     *
     * @param errors The errors for throwing the {@link CustomerValidatorException}.
     */
    public CustomerValidatorException(Set<CustomerError> errors)
    {
        this.errors = errors;
    }

    /**
     * Creates a new {@link CustomerValidatorException}.
     *
     * @param reason The reason for throwing the {@link CustomerValidatorException}.
     */
    public CustomerValidatorException(CustomerError reason)
    {
        this.errors = new HashSet<>();
        this.errors.add(reason);
    }

    /**
     * Checks if the provided reason is a cause of the {@link CustomerValidatorException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason is a cause of the {@link CustomerValidatorException}.
     */
    public boolean hasError(CustomerError reason)
    {
        return errors.contains(reason);
    }

    /**
     * Returns the errors for throwing the {@link CustomerValidatorException}.
     *
     * @return The errors for throwing the {@link CustomerValidatorException}.
     */
    public Set<CustomerError> getErrors()
    {
        return this.errors;
    }
}
