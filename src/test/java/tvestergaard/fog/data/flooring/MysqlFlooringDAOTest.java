package tvestergaard.fog.data.flooring;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlFlooringDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlFlooringDAO dao    = new MysqlFlooringDAO(source);

    @Before
    public void createData() throws Exception
    {
        Connection connection = source.getConnection();
        connection.createStatement().execute("DELETE FROM floorings");
        connection.createStatement().execute(
                "INSERT INTO floorings (id, name, description, price_per_square_meter, active) " +
                        "VALUES (1, 'name1', 'description1', 1, b'1')");
        connection.createStatement().execute(
                "INSERT INTO floorings (id, name, description, price_per_square_meter, active) " +
                        "VALUES (2, 'name2', 'description2', 2, b'1')");
        connection.createStatement().execute(
                "INSERT INTO floorings (id, name, description, price_per_square_meter, active)" +
                        " VALUES (3, 'name3', 'description3', 3, b'1')");
        connection.createStatement().execute(
                "INSERT INTO floorings (id, name, description, price_per_square_meter, active)" +
                        " VALUES (4, 'name4', 'description4', 4, b'1')");
        connection.createStatement().execute(
                "INSERT INTO floorings (id, name, description, price_per_square_meter, active)" +
                        " VALUES (5, 'name5', 'description5', 5, b'1')");
    }

    @Test
    public void getAll() throws Exception
    {
        List<Flooring> floorings = dao.get();

        assertEquals(5, floorings.size());
        assertEquals(1, floorings.get(0).getId());
        assertEquals(2, floorings.get(1).getId());
        assertEquals(3, floorings.get(2).getId());
        assertEquals(4, floorings.get(3).getId());
        assertEquals(5, floorings.get(4).getId());
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Flooring> floorings = dao.get(where(eq("id", 1)));

        assertEquals(1, floorings.size());

        Flooring flooring = floorings.get(0);
        assertEquals(1, flooring.getId());
        assertEquals("name1", flooring.getName());
        assertEquals("description1", flooring.getDescription());
        assertEquals(1, flooring.getPricePerSquareMeter());
        assertEquals(true, flooring.isActive());
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Flooring> floorings = dao.get(where(like("name", "name%")));

        assertEquals(5, floorings.size());
        assertEquals("name1", floorings.get(0).getName());
        assertEquals("name2", floorings.get(1).getName());
        assertEquals("name3", floorings.get(2).getName());
        assertEquals("name4", floorings.get(3).getName());
        assertEquals("name5", floorings.get(4).getName());
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Flooring> floorings = dao.get(order("name", desc()));

        assertEquals(5, floorings.size());
        assertEquals("name5", floorings.get(0).getName());
        assertEquals("name4", floorings.get(1).getName());
        assertEquals("name3", floorings.get(2).getName());
        assertEquals("name2", floorings.get(3).getName());
        assertEquals("name1", floorings.get(4).getName());
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Flooring> floorings = dao.get(limit(2));

        assertEquals(2, floorings.size());
        assertEquals(1, floorings.get(0).getId());
        assertEquals(2, floorings.get(1).getId());
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Flooring> floorings = dao.get(limit(2), offset(1));

        assertEquals(2, floorings.get(0).getId());
        assertEquals(3, floorings.get(1).getId());
    }
}