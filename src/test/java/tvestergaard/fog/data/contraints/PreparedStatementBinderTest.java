package tvestergaard.fog.data.contraints;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static tvestergaard.fog.data.contraints.Constraint.eq;
import static tvestergaard.fog.data.contraints.Constraint.where;

public class PreparedStatementBinderTest
{
    private static final MysqlDataSource         source = TestDataSource.getSource();
    private final        PreparedStatementBinder binder = new PreparedStatementBinder();

    @BeforeClass
    public static void before() throws Exception
    {
        Connection c = source.getConnection();
        c.createStatement().executeUpdate("DROP table IF exists `binder-test`;");
        c.createStatement().executeUpdate("CREATE TABLE `binder-test` ( `id` int(11) not null, `name` varchar(255) not null )");
    }

    @Before
    public void setUp() throws Exception
    {
        Connection c = source.getConnection();
        c.createStatement().executeUpdate("INSERT INTO `binder-test` VALUES (0, 'nameA');");
        c.createStatement().executeUpdate("INSERT INTO `binder-test` VALUES (1, 'nameB');");
        c.createStatement().executeUpdate("INSERT INTO `binder-test` VALUES (2, 'nameC');");
        c.createStatement().executeUpdate("INSERT INTO `binder-test` VALUES (3, 'nameD');");
        c.createStatement().executeUpdate("INSERT INTO `binder-test` VALUES (4, 'nameE');");
    }

    @Test
    public void bindWhere() throws Exception
    {
        Connection        c         = source.getConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `binder-test` WHERE `name` = ?");
        binder.bind(statement, where(eq("column", "nameC")));
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals(2, resultSet.getInt("id"));
        assertEquals("nameC", resultSet.getString("name"));
        assertFalse(resultSet.next());
    }

    @Test
    public void bindLike() throws Exception
    {
        Connection        c         = source.getConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `binder-test` WHERE `name` LIKE ?");
        binder.bind(statement, where(eq("column", "name%")));
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals(0, resultSet.getInt("id"));
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt("id"));
        assertTrue(resultSet.next());
        assertEquals(2, resultSet.getInt("id"));
        assertTrue(resultSet.next());
        assertEquals(3, resultSet.getInt("id"));
        assertTrue(resultSet.next());
        assertEquals(4, resultSet.getInt("id"));
    }
}