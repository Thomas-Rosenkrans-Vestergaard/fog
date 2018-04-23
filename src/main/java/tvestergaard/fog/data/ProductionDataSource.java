package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ProductionDataSource
{

    private static MysqlDataSource source;

    public static MysqlDataSource getSource()
    {
        if (source == null) {
            source = new MysqlDataSource();
            source.setDatabaseName("fog");
            source.setUser("fog");
        }

        return source;
    }
}
