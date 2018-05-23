package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

public class TestDataSource
{

    private static MysqlDataSource source;

    public static MysqlDataSource getSource()
    {
        if (source == null) {
            source = new CachedMysqlDataSource();
            source.setDatabaseName("fog-test");
            source.setUser("fog");
        }

        return source;
    }
}
