package tvestergaard.fog.data.constraints;

import java.util.Arrays;

/**
 * Represents a condition that must be true for some entity to be returned during some selection operation.
 */
public class WhereConstraint<C extends Enum<C>> implements Constraint<C>
{

    /**
     * The conditions defined in the {@link WhereConstraint}.
     */
    private final WhereCondition<C>[] conditions;

    /**
     * Creates a new {@link WhereConstraint}.
     *
     * @param conditions The conditions defined in the {@link WhereConstraint}.
     */
    public WhereConstraint(WhereCondition<C>... conditions)
    {
        this.conditions = conditions;
    }

    /**
     * Returns the conditions of the {@link WhereConstraint}. The array returned can be modified without altering the
     * internal representation of the array.
     *
     * @return The conditions of the {@link WhereConstraint}.
     */
    public WhereCondition<C>[] getConditions()
    {
        return Arrays.copyOf(this.conditions, this.conditions.length);
    }
}
