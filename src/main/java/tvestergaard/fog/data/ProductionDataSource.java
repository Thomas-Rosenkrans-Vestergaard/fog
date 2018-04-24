package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Returns the {@link MysqlDataSource} used to access the database.
 */
public class ProductionDataSource
{

    /**
     *
     */
    private static MysqlDataSource source;

    public static MysqlDataSource getSource()
    {
        if (source == null) {
            source = new MysqlDataSource();
            source.setDatabaseName("fogdb");
            source.setUser("root");
            source.setPassword("Hightech4u");
        }

        return source;
    }
}
