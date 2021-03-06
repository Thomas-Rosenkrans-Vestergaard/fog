package tvestergaard.fog.data.flooring;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.constraints.OrderDirection;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.flooring.FlooringColumn.ID;
import static tvestergaard.fog.data.flooring.FlooringColumn.NAME;

public class MysqlFlooringDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlFlooringDAO dao    = new MysqlFlooringDAO(source);

    private Flooring flooring1;
    private Flooring flooring2;
    private Flooring flooring3;
    private Flooring flooring4;
    private Flooring flooring5;

    @Before
    public void createData() throws Exception
    {
        flooring1 = dao.create(FlooringBlueprint.from("name1", "description1", true));
        flooring2 = dao.create(FlooringBlueprint.from("name2", "description2", false));
        flooring3 = dao.create(FlooringBlueprint.from("name3", "description3", false));
        flooring4 = dao.create(FlooringBlueprint.from("name4", "description4", true));
        flooring5 = dao.create(FlooringBlueprint.from("name5", "description5", false));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM floorings");
    }

    @Test
    public void get() throws Exception
    {
        List<Flooring> floorings = dao.get(null);

        assertEquals(5, floorings.size());
        assertEquals(flooring1, floorings.get(0));
        assertEquals(flooring2, floorings.get(1));
        assertEquals(flooring3, floorings.get(2));
        assertEquals(flooring4, floorings.get(3));
        assertEquals(flooring5, floorings.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Flooring> floorings = dao.get(where(eq(ID, flooring1.getId())));

        assertEquals(1, floorings.size());
        assertEquals(flooring1, floorings.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Flooring> floorings = dao.get(where(like(NAME, "name%")));

        assertEquals(5, floorings.size());
        assertEquals(flooring1, floorings.get(0));
        assertEquals(flooring2, floorings.get(1));
        assertEquals(flooring3, floorings.get(2));
        assertEquals(flooring4, floorings.get(3));
        assertEquals(flooring5, floorings.get(4));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Flooring> floorings = dao.get(order(NAME, OrderDirection.DESC));

        assertEquals(5, floorings.size());
        assertEquals(flooring5, floorings.get(0));
        assertEquals(flooring4, floorings.get(1));
        assertEquals(flooring3, floorings.get(2));
        assertEquals(flooring2, floorings.get(3));
        assertEquals(flooring1, floorings.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Flooring> floorings = dao.get(limit(FlooringColumn.class, 2));

        assertEquals(2, floorings.size());
        assertEquals(flooring1, floorings.get(0));
        assertEquals(flooring2, floorings.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Flooring> floorings = dao.get(limit(FlooringColumn.class, 2).offset(1));

        assertEquals(flooring2, floorings.get(0));
        assertEquals(flooring3, floorings.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(flooring3, dao.first(where(eq(ID, flooring3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String   name        = "some_random_name";
        String   description = "some_random_description";
        boolean  active      = false;
        Flooring actual      = dao.create(FlooringBlueprint.from(name, description, active));
        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        flooring1.setName("new_name");
        flooring1.setDescription("new_description");
        flooring1.setActive(true);

        assertTrue(dao.update(flooring1));

        List<Flooring> actual = dao.get(where(eq(ID, flooring1.getId())));
        assertEquals(flooring1, actual.get(0));
    }
}