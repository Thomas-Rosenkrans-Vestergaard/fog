package tvestergaard.fog.data.clauses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Generator
{

    public String generateSQL(String statement, ClauseCollection clauses)
    {
        StringBuilder builder     = new StringBuilder(statement);
        Clause[]      clauseArray = clauses.getClauses();
        Arrays.sort(clauseArray, (o, t) -> {
            return o.getOrder() - t.getOrder();
        });

        for (Clause clause : clauseArray)
            clause.appendSQL(builder);

        return builder.toString();
    }

    /**
     * Sets the parameters used by the provided {@link Clause}s.
     *
     * @param statement The statements to set the parameters on.
     * @param begin     The parameter index to begin to set the parameters at.
     * @param clauses   The clauses to apply the parameters of.
     * @throws SQLException When a database exception occurs.
     */
    public void setParameters(PreparedStatement statement, int begin, ClauseCollection clauses) throws SQLException
    {
        ParameterIndex counter = new ParameterIndex();
        counter.index = begin;

        for (Clause clause : clauses.getClauses())
            clause.setParameters(statement, counter);
    }

    /**
     * Sets the parameters used by the provided {@link Clause}s. Begins setting parameters from index 1.
     *
     * @param statement The statements to set the parameters on.
     * @param clauses   The clauses to apply the parameters of.
     * @throws SQLException When a database exception occurs.
     */
    public void setParameters(PreparedStatement statement, ClauseCollection clauses) throws SQLException
    {
        setParameters(statement, 1, clauses);
    }

    public class ParameterIndex
    {
        public int index = 1;
    }
}
