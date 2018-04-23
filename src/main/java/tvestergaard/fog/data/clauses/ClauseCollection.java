package tvestergaard.fog.data.clauses;

/**
 * A collection of {@link Clause} instances.
 */
public class ClauseCollection
{

    /**
     * The {@link Clause} instances.
     */
    private final Clause[] clauses;

    /**
     * The number of clauses.
     */
    public final int length;

    /**
     * Creates a new {@link ClauseCollection}.
     *
     * @param clauses The {@link Clause} instances.
     */
    public ClauseCollection(Clause... clauses)
    {
        this.clauses = clauses;
        this.length = clauses.length;
    }

    /**
     * Returns the {@link Clause} instances.
     *
     * @return The {@link Clause} instances.
     */
    public Clause[] getClauses()
    {
        return this.clauses;
    }
}
