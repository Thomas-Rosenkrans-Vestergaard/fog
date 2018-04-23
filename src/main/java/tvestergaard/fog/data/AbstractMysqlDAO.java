package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractMysqlDAO
{

    /**
     * The {@link MysqlDataSource} to perform operations upon.
     */
    private final MysqlDataSource source;

    /**
     * The cahced connection for reuse.
     */
    private Connection connection = null;

    /**
     * Creates a new {@link AbstractMysqlDAO}.
     *
     * @param source The {@link MysqlDataSource} to perform operations upon.
     */
    public AbstractMysqlDAO(MysqlDataSource source)
    {
        this.source = source;
    }

    /**
     * Returns a connection from the provided {@code MysqlDataSource} with autocommit disabled.
     *
     * @return The connection from the provided {@code MysqlDataSource} with autocommit disabled.
     * @throws SQLException
     */
    protected final Connection getConnection() throws SQLException
    {
        if (connection == null) {
            connection = source.getConnection();
            connection.setAutoCommit(false);
        }

        return connection;
    }
}
