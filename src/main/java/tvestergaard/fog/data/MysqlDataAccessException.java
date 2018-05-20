package tvestergaard.fog.data;

import java.sql.SQLException;

public class MysqlDataAccessException extends DataAccessException
{

    /**
     * The {@link SQLException} that caused the {@link MysqlDataAccessException}.
     */
    private final SQLException exception;

    /**
     * Creates a new {@link MysqlDataAccessException}.
     *
     * @param exception The {@link SQLException} that caused the {@link MysqlDataAccessException}.
     */
    public MysqlDataAccessException(SQLException exception)
    {
        super(exception);
        this.exception = exception;
    }

    /**
     * Returns the {@link SQLException} that caused the {@link MysqlDataAccessException}.
     *
     * @return The {@link SQLException} that caused the {@link MysqlDataAccessException}.
     */
    @Override public synchronized SQLException getCause()
    {
        return exception;
    }
}
