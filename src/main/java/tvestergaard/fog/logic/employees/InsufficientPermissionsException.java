package tvestergaard.fog.logic.employees;

import tvestergaard.fog.data.employees.Role;

public class InsufficientPermissionsException extends Exception
{

    /**
     * The role permission that was required.
     */
    private final Role required;

    /**
     * Creates a new {@link InsufficientPermissionsException}.
     *
     * @param required The role permission that was required.
     */
    public InsufficientPermissionsException(Role required)
    {
        this.required = required;
    }

    /**
     * Returns the role permission that was required.
     *
     * @return The role permission that was required.
     */
    public Role getRequired()
    {
        return this.required;
    }
}
