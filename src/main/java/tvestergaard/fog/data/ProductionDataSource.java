package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Returns the {@link MysqlDataSource} used to access the database.
 */
public class ProductionDataSource
{

    private static MysqlDataSource source;

    public static MysqlDataSource getSource()
    {
        if (source == null) {
            source = new CachedMysqlDataSource();
            source.setDatabaseName("fog");
            source.setUser("fog");
            source.setURL("jdbc:mysql://localhost:3306/fog?user=fog&useUnicode=yes&characterEncoding=UTF8&autoReconnect=true");
        }

        return source;
    }
}
