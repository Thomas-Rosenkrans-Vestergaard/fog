package tvestergaard.fog.data.contraints;

/**
 * Restricts the number of records returned by some select operation the object is provided to.
 */
public class LimitConstraint implements Constraint
{

    /**
     * The maximum number of records to return.
     */
    public final int limit;

    /**
     * Creates a new {@link LimitConstraint}.
     *
     * @param limit The maximum number of records to return.
     */
    public LimitConstraint(int limit)
    {
        this.limit = limit;
    }

    /**
     * Returns the maximum number of records to return.
     *
     * @return The maximum number of records to return.
     */
    public int getLimit()
    {
        return this.limit;
    }
}
