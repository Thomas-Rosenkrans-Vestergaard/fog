package tvestergaard.fog.logic.materials;

import java.util.HashSet;
import java.util.Set;

public class MaterialValidatorException extends Exception
{

    /**
     * The errors that caused the {@link MaterialValidatorException}.
     */
    private final Set<MaterialError> errors;

    /**
     * Creates a new {@link MaterialValidatorException}.
     *
     * @param errors The errors that caused the {@link MaterialValidatorException}.
     */
    public MaterialValidatorException(Set<MaterialError> errors)
    {
        this.errors = errors;
    }

    /**
     * Creates a new {@link MaterialValidatorException}.
     *
     * @param error The error that caused the {@link MaterialValidatorException}.
     */
    public MaterialValidatorException(MaterialError error)
    {
        this.errors = new HashSet<>();
        this.errors.add(error);
    }

    /**
     * Returns the errors that caused the {@link MaterialValidatorException}.
     *
     * @return The errors that caused the {@link MaterialValidatorException}.
     */
    public Set<MaterialError> getErrors()
    {
        return this.errors;
    }
}
