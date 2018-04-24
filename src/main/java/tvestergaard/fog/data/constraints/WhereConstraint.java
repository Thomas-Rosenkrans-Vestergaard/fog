package tvestergaard.fog.data.constraints;

import java.util.Arrays;

/**
 * Represents a condition that must be true for some entity to be returned during some selection operation.
 */
public class WhereConstraint implements Constraint
{

    /**
     * The conditions defined in the {@link WhereConstraint}.
     */
    private final WhereCondition[] conditions;

    /**
     * Creates a new {@link WhereConstraint}.
     *
     * @param conditions The conditions defined in the {@link WhereConstraint}.
     */
    public WhereConstraint(WhereCondition... conditions)
    {
        this.conditions = conditions;
    }

    /**
     * Returns the conditions of the {@link WhereConstraint}. The array returned can be modified without altering the
     * internal representation of the array.
     *
     * @return The conditions of the {@link WhereConstraint}.
     */
    public WhereCondition[] getConditions()
    {
        return Arrays.copyOf(this.conditions, this.conditions.length);
    }
}
