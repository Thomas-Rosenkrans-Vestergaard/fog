package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class CachedMysqlDataSource extends MysqlDataSource
{

    /**
     * The cached connection.
     */
    private Connection cachedConnection;

    /**
     * Creates a new connection using the already configured username and
     * password.
     *
     * @return a connection to the database
     * @throws SQLException if an error occurs
     */
    @Override public Connection getConnection() throws SQLException
    {
        if (cachedConnection == null) {
            cachedConnection = super.getConnection();
            cachedConnection.setAutoCommit(false);
        }

        return cachedConnection;
    }
}
