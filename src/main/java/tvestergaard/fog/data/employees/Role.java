package tvestergaard.fog.data.employees;

public enum Role
{
    HEAD_OF_CENTER(1),
    HEAD_OF_MATERIALS(2),
    SALESMAN(3);

    /**
     * The unique identifier of the role.
     */
    private final int id;

    /**
     * Creates a new role.
     *
     * @param id The unique identifier of the role.
     */
    Role(int id)
    {
        this.id = id;
    }

    /**
     * Returns the unique identifier of the role.
     *
     * @return The unique identifier of the role.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the {@link Role} with the provided id.
     *
     * @param id The if of the role to return.
     */
    public static Role from(int id)
    {
        if (id == 1)
            return HEAD_OF_CENTER;
        if (id == 2)
            return HEAD_OF_MATERIALS;
        if (id == 3)
            return SALESMAN;

        throw new IllegalStateException("Unknown role with provided id.");
    }
}
