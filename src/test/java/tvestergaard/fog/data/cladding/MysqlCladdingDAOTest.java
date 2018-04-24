package tvestergaard.fog.data.cladding;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;
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
    public void createData() throws Exception
    {
        Connection connection = source.getConnection();
        connection.createStatement().execute("DELETE FROM claddings");
        cladding1 = dao.create("name1", "description1", 1, false);
        cladding2 = dao.create("name2", "description2", 2, true);
        cladding3 = dao.create("name3", "description3", 3, false);
        cladding4 = dao.create("name4", "description4", 4, true);
        cladding5 = dao.create("name5", "description5", 5, false);
    }

    @Test
    public void get() throws Exception
    {
        List<Cladding> claddings = dao.get();

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
        List<Cladding> claddings = dao.get(where(eq("id", cladding1.getId())));

        assertEquals(1, claddings.size());
        assertEquals(cladding1, claddings.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Cladding> claddings = dao.get(where(like("name", "name%")));

        assertEquals(5, claddings.size());
        assertEquals(cladding1, claddings.get(0));
        assertEquals(cladding2, claddings.get(1));
        assertEquals(cladding3, claddings.get(2));
        assertEquals(cladding4, claddings.get(3));
        assertEquals(cladding5, claddings.get(4));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Cladding> claddings = dao.get(order("name", desc()));

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
        List<Cladding> claddings = dao.get(limit(2));

        assertEquals(2, claddings.size());
        assertEquals(cladding1, claddings.get(0));
        assertEquals(cladding2, claddings.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Cladding> claddings = dao.get(limit(2), offset(1));

        assertEquals(cladding2, claddings.get(0));
        assertEquals(cladding3, claddings.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(cladding3, dao.first(where(eq("id", cladding3.getId()))));
        assertNull(dao.first(where(eq("id", -1))));
    }

    @Test
    public void create() throws Exception
    {
        String   name                = "some_random_name";
        String   description         = "some_random_description";
        int      pricePerSquareMeter = 234873;
        boolean  active              = false;
        Cladding actual              = dao.create(name, description, pricePerSquareMeter, active);
        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        assertEquals(pricePerSquareMeter, actual.getPricePerSquareMeter());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        cladding1.setName("new_name");
        cladding1.setDescription("new_description");
        cladding1.setPricePerSquareMeter(2897342);
        cladding1.setActive(true);

        assertTrue(dao.update(cladding1));

        List<Cladding> actual = dao.get(where(eq("id", cladding1.getId())));
        assertEquals(cladding1, actual.get(0));
    }
}