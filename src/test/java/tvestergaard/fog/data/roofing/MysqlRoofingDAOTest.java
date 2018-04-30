package tvestergaard.fog.data.roofing;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;
import static tvestergaard.fog.data.roofing.RoofingColumn.NAME;

public class MysqlRoofingDAOTest
{

    private static final MysqlDataSource source = TestDataSource.getSource();
    private static final MysqlRoofingDAO dao    = new MysqlRoofingDAO(source);

    private Roofing roofing1;
    private Roofing roofing2;
    private Roofing roofing3;
    private Roofing roofing4;
    private Roofing roofing5;

    @Before
    public void createData() throws Exception
    {
        roofing1 = dao.create(RoofingBlueprint.from("name1", "description1", 1, 1, true));
        roofing2 = dao.create(RoofingBlueprint.from("name2", "description2", 2, 2, false));
        roofing3 = dao.create(RoofingBlueprint.from("name3", "description3", 3, 3, true));
        roofing4 = dao.create(RoofingBlueprint.from("name4", "description4", 4, 4, false));
        roofing5 = dao.create(RoofingBlueprint.from("name5", "description5", 5, 5, true));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM roofings");
    }

    @Test
    public void get() throws Exception
    {
        List<Roofing> roofings = dao.get();

        assertEquals(5, roofings.size());
        assertEquals(roofing1, roofings.get(0));
        assertEquals(roofing2, roofings.get(1));
        assertEquals(roofing3, roofings.get(2));
        assertEquals(roofing4, roofings.get(3));
        assertEquals(roofing5, roofings.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Roofing> roofings = dao.get(where(eq(ID, roofing1.getId())));

        assertEquals(1, roofings.size());
        assertEquals(roofing1, roofings.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Roofing> roofings = dao.get(where(like(NAME, "name%")));

        assertEquals(5, roofings.size());
        assertEquals(roofing1, roofings.get(0));
        assertEquals(roofing2, roofings.get(1));
        assertEquals(roofing3, roofings.get(2));
        assertEquals(roofing4, roofings.get(3));
        assertEquals(roofing5, roofings.get(4));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Roofing> roofings = dao.get(order(RoofingColumn.NAME, desc()));

        assertEquals(5, roofings.size());
        assertEquals(roofing5, roofings.get(0));
        assertEquals(roofing4, roofings.get(1));
        assertEquals(roofing3, roofings.get(2));
        assertEquals(roofing2, roofings.get(3));
        assertEquals(roofing1, roofings.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Roofing> roofings = dao.get(limit(2));

        assertEquals(2, roofings.size());
        assertEquals(roofing1, roofings.get(0));
        assertEquals(roofing2, roofings.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Roofing> roofings = dao.get(limit(2), offset(1));

        assertEquals(roofing2, roofings.get(0));
        assertEquals(roofing3, roofings.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(roofing3, dao.first(where(eq(ID, roofing3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String           name         = "some_random_name";
        String           description  = "some_random_description";
        int              minimumSlope = 34;
        int              maximumSlope = 77;
        boolean          active       = false;
        RoofingBlueprint blueprint    = RoofingBlueprint.from(name, description, minimumSlope, maximumSlope, active);
        Roofing          actual       = dao.create(blueprint);
        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        assertEquals(minimumSlope, actual.getMinimumSlope());
        assertEquals(maximumSlope, actual.getMaximumSlope());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        roofing1.setName(randomString());
        roofing1.setDescription(randomString());
        roofing1.setMinimumSlope(45);
        roofing1.setMaximumSlope(90);
        roofing1.setActive(true);

        assertTrue(dao.update(roofing1));

        List<Roofing> actual = dao.get(where(eq(ID, roofing1.getId())));
        assertEquals(roofing1, actual.get(0));
    }
}