package tvestergaard.fog.data.constraints;

/**
 * Changes the ordering of some select operation.
 */
public class OrderConstraint implements Constraint
{

    /**
     * The column to order by.
     */
    public final String column;

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
    public OrderConstraint(String column, Direction direction)
    {
        this.column = column;
        this.direction = direction;
    }

    /**
     * Returns the column to order by.
     *
     * @return The column to order by.
     */
    public String getColumn()
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
