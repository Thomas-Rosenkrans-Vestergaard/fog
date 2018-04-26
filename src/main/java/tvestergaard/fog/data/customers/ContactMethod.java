package tvestergaard.fog.data.customers;

/**
 * Represents a way the {@link Customer} can be contacted.
 */
public enum ContactMethod
{

    /**
     * The {@link Customer} prefers to be contacted by email.
     */
    EMAIL(0),

    /**
     * The {@link Customer} prefers to be contacted by phone.
     */
    PHONE(1);

    /**
     * The id representing the {@link ContactMethod}.
     */
    public final int id;

    /**
     * Creates a new {@link ContactMethod}.
     *
     * @param id The id representing the {@link ContactMethod}.
     */
    ContactMethod(int id)
    {
        this.id = id;
    }

    /**
     * Returns the {@link ContactMethod} with the provided identifier.
     *
     * @param id The identifier.
     * @return The {@link ContactMethod} with the provided identifier.
     */
    public static ContactMethod from(int id)
    {
        if (id == 0)
            return EMAIL;
        if (id == 1)
            return PHONE;

        throw new IllegalArgumentException("No contact method with provided id.");
    }
}
