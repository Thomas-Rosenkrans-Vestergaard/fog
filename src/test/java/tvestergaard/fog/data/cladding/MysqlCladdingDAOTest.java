package tvestergaard.fog.data.cladding;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.constraints.OrderDirection;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.cladding.CladdingColumn.NAME;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlCladdingDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlCladdingDAO dao    = new MysqlCladdingDAO(source);

    private Cladding cladding1;
    private Cladding cladding2;
    private Cladding cladding3;
    private Cladding cladding4;
    private Cladding cladding5;

    @Before
    public void before() throws Exception
    {
        cladding1 = dao.create(CladdingBlueprint.from("name1", "description1", false));
        cladding2 = dao.create(CladdingBlueprint.from("name2", "description2", true));
        cladding3 = dao.create(CladdingBlueprint.from("name3", "description3", false));
        cladding4 = dao.create(CladdingBlueprint.from("name4", "description4", true));
        cladding5 = dao.create(CladdingBlueprint.from("name5", "description5", false));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM claddings");
    }

    @Test
    public void get() throws Exception
    {
        List<Cladding> claddings = dao.get(null);

        assertEquals(5, claddings.size());
        assertEquals(cladding1, claddings.get(0));
        assertEquals(cladding2, claddings.get(1));
        assertEquals(cladding3, claddings.get(2));
        assertEquals(cladding4, claddings.get(3));
        assertEquals(cladding5, claddings.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Cladding> claddings = dao.get(where(eq(ID, cladding1.getId())));

        assertEquals(1, claddings.size());
        assertEquals(cladding1, claddings.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Cladding> claddings = dao.get(where(like(NAME, "name%")));

        assertEquals(cladding1, claddings.get(claddings.size() - 5));
        assertEquals(cladding2, claddings.get(claddings.size() - 4));
        assertEquals(cladding3, claddings.get(claddings.size() - 3));
        assertEquals(cladding4, claddings.get(claddings.size() - 2));
        assertEquals(cladding5, claddings.get(claddings.size() - 1));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Cladding> claddings = dao.get(order(ID, OrderDirection.DESC));

        assertEquals(5, claddings.size());
        assertEquals(cladding5, claddings.get(0));
        assertEquals(cladding4, claddings.get(1));
        assertEquals(cladding3, claddings.get(2));
        assertEquals(cladding2, claddings.get(3));
        assertEquals(cladding1, claddings.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Cladding> claddings = dao.get(limit(CladdingColumn.class, 2));

        assertEquals(2, claddings.size());
        assertEquals(cladding1, claddings.get(0));
        assertEquals(cladding2, claddings.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Cladding> claddings = dao.get(limit(CladdingColumn.class, 2));

        assertEquals(cladding2, claddings.get(0));
        assertEquals(cladding3, claddings.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(cladding3, dao.first(where(eq(ID, cladding3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String   name        = randomString();
        String   description = randomString();
        boolean  active      = randomBoolean();
        Cladding actual      = dao.create(CladdingBlueprint.from(name, description, active));
        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        cladding1.setName(randomString());
        cladding1.setDescription(randomString());
        cladding1.setActive(randomBoolean());

        assertTrue(dao.update(cladding1));

        List<Cladding> actual = dao.get(where(eq(ID, cladding1.getId())));
        assertEquals(cladding1, actual.get(0));
    }
}