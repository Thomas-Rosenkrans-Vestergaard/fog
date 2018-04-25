package tvestergaard.fog.data.constraints;

/**
 * Changes the ordering of some select operation.
 */
public class OrderConstraint<C extends Enum<C>> implements Constraint<C>
{

    /**
     * The column to order by.
     */
    public final C column;

    /**
     * The direction to order in.
     */
    public final Direction direction;

    /**
     * Creates a new {@link OrderConstraint} selector.
     *
     * @param column    The column to order by.
     * @param direction The direction to order in.
     */
    public OrderConstraint(C column, Direction direction)
    {
        this.column = column;
        this.direction = direction;
    }

    /**
     * Returns the column to order by.
     *
     * @return The column to order by.
     */
    public C getColumn()
    {
        return this.column;
    }

    /**
     * Returns the direction to order in.
     *
     * @return The direction to order in.
     */
    public Direction getDirection()
    {
        return this.direction;
    }

    /**
     * Represents a direction to order in.
     */
    public enum Direction
    {
        ASC,
        DESC
    }
}
