package tvestergaard.fog.logic.offers;

import java.util.HashSet;
import java.util.Set;

public class OfferValidatorException extends Exception
{

    /**
     * The errors that caused the {@link OfferValidatorException}.
     */
    private final Set<OfferError> errors;

    /**
     * Creates a new {@link OfferValidatorException}.
     *
     * @param errors The errors that caused the {@link OfferValidatorException}.
     */
    public OfferValidatorException(Set<OfferError> errors)
    {
        this.errors = errors;
    }

    /**
     * Creates a new {@link OfferValidatorException}.
     *
     * @param error The error that caused the {@link OfferValidatorException}.
     */
    public OfferValidatorException(OfferError error)
    {
        this.errors = new HashSet<>();
        this.errors.add(error);
    }

    /**
     * Returns the errors that caused the {@link OfferValidatorException}.
     *
     * @return The errors that caused the {@link OfferValidatorException}.
     */
    public Set<OfferError> getErrors()
    {
        return this.errors;
    }
}
