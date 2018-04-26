package tvestergaard.fog.data.orders;

/**
 * Represents the rafters chosen by some customer, when an order was placed.
 */
public enum RafterChoice
{

    /**
     * The customer receives prebuilt rafters.
     */
    PREBUILT(0),

    /**
     * The customer makes the rafters themselves.
     */
    BUILD_SELF(1);

    /**
     * The unique identifier representing the choice of rafter.
     */
    private final int id;

    /**
     * Creates a new {@link RafterChoice}.
     *
     * @param id The identifier representing the choice of rafter.
     */
    RafterChoice(int id)
    {
        this.id = id;
    }

    /**
     * Returns the rafter with the provided id.
     *
     * @param id The identifier of the rafter to return.
     * @return The rafter with the provided id.
     */
    public static RafterChoice from(int id)
    {
        if (id == 0)
            return PREBUILT;
        if (id == 1)
            return BUILD_SELF;

        throw new IllegalArgumentException("Unknown Rafter with provided id.");
    }

    /**
     * Returns the identifier representing the choice of rafter.
     *
     * @return The identifier representing the choice of rafter.
     */
    public int getId()
    {
        return this.id;
    }
}
