package tvestergaard.fog.data.roofing;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlRoofingDAOTest
{

    private static final MysqlDataSource source = TestDataSource.getSource();
    private static final MysqlRoofingDAO dao    = new MysqlRoofingDAO(source);

    @Before
    public void createData() throws Exception
    {
        Connection connection = source.getConnection();
        connection.createStatement().execute("DELETE FROM roofings");
        connection.createStatement().execute(
                "INSERT INTO roofings (id, name, description, minimum_slope, maximum_slope, price_per_square_meter, active) " +
                        "VALUES (1, 'name1', 'description1', 5, 5, 1, b'1')");
        connection.createStatement().execute(
                "INSERT INTO roofings (id, name, description, minimum_slope, maximum_slope, price_per_square_meter, active) " +
                        "VALUES (2, 'name2', 'description2', 5, 5, 2, b'1')");
        connection.createStatement().execute(
                "INSERT INTO roofings (id, name, description, minimum_slope, maximum_slope, price_per_square_meter, active)" +
                        " VALUES (3, 'name3', 'description3', 5, 5, 3, b'1')");
        connection.createStatement().execute(
                "INSERT INTO roofings (id, name, description, minimum_slope, maximum_slope, price_per_square_meter, active)" +
                        " VALUES (4, 'name4', 'description4', 5, 5, 4, b'1')");
        connection.createStatement().execute(
                "INSERT INTO roofings (id, name, description, minimum_slope, maximum_slope, price_per_square_meter, active)" +
                        " VALUES (5, 'name5', 'description5', 5, 5, 5, b'1')");
    }

    @Test
    public void getAll() throws Exception
    {
        List<Roofing> roofings = dao.get();

        assertEquals(5, roofings.size());
        assertEquals(1, roofings.get(0).getId());
        assertEquals(2, roofings.get(1).getId());
        assertEquals(3, roofings.get(2).getId());
        assertEquals(4, roofings.get(3).getId());
        assertEquals(5, roofings.get(4).getId());
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Roofing> roofings = dao.get(where(eq("id", 1)));

        assertEquals(1, roofings.size());

        Roofing roofing = roofings.get(0);
        assertEquals(1, roofing.getId());
        assertEquals("name1", roofing.getName());
        assertEquals("description1", roofing.getDescription());
        assertEquals(5, roofing.getMinimumSlope());
        assertEquals(5, roofing.getMaximumSlope());
        assertEquals(1, roofing.getPricePerSquareMeter());
        assertEquals(true, roofing.isActive());
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Roofing> roofings = dao.get(where(like("name", "name%")));

        assertEquals(5, roofings.size());
        assertEquals("name1", roofings.get(0).getName());
        assertEquals("name2", roofings.get(1).getName());
        assertEquals("name3", roofings.get(2).getName());
        assertEquals("name4", roofings.get(3).getName());
        assertEquals("name5", roofings.get(4).getName());
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Roofing> roofings = dao.get(order("name", desc()));

        assertEquals(5, roofings.size());
        assertEquals("name5", roofings.get(0).getName());
        assertEquals("name4", roofings.get(1).getName());
        assertEquals("name3", roofings.get(2).getName());
        assertEquals("name2", roofings.get(3).getName());
        assertEquals("name1", roofings.get(4).getName());
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Roofing> roofings = dao.get(limit(2));

        assertEquals(2, roofings.size());
        assertEquals(1, roofings.get(0).getId());
        assertEquals(2, roofings.get(1).getId());
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Roofing> roofings = dao.get(limit(2), offset(1));

        assertEquals(2, roofings.get(0).getId());
        assertEquals(3, roofings.get(1).getId());
    }
}