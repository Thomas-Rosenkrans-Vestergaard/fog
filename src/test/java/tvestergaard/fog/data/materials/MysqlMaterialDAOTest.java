package tvestergaard.fog.data.materials;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.materials.MaterialColumn.DESCRIPTION;
import static tvestergaard.fog.data.materials.MaterialColumn.ID;

public class MysqlMaterialDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlMaterialDAO dao    = new MysqlMaterialDAO(source);

    private Material material1;
    private Material material2;
    private Material material3;
    private Material material4;
    private Material material5;

    @Before
    public void before() throws Exception
    {
        material1 = dao.create(Material.blueprint("number1", "description1", "notes1", 1, 6, 11));
        material2 = dao.create(Material.blueprint("number2", "description2", "notes2", 2, 7, 12));
        material3 = dao.create(Material.blueprint("number3", "description3", "notes3", 3, 8, 13));
        material4 = dao.create(Material.blueprint("number4", "description4", "notes4", 4, 9, 14));
        material5 = dao.create(Material.blueprint("number5", "description5", "notes5", 5, 10, 15));
    }

    @After
    public void after() throws Exception
    {
        TestDataSource.getSource().getConnection().createStatement().executeUpdate("DELETE FROM materials");
    }

    @Test
    public void get() throws Exception
    {
        List<Material> materials = dao.get();

        assertEquals(5, materials.size());
        assertEquals(material1, materials.get(0));
        assertEquals(material2, materials.get(1));
        assertEquals(material3, materials.get(2));
        assertEquals(material4, materials.get(3));
        assertEquals(material5, materials.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Material> materials = dao.get(where(eq(ID, material1.getId())));

        assertEquals(1, materials.size());
        assertEquals(material1, materials.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Material> materials = dao.get(where(like(DESCRIPTION, "description%")));

        assertEquals(material1, materials.get(materials.size() - 5));
        assertEquals(material2, materials.get(materials.size() - 4));
        assertEquals(material3, materials.get(materials.size() - 3));
        assertEquals(material4, materials.get(materials.size() - 2));
        assertEquals(material5, materials.get(materials.size() - 1));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Material> materials = dao.get(order(ID, desc()));

        assertEquals(5, materials.size());
        assertEquals(material5, materials.get(0));
        assertEquals(material4, materials.get(1));
        assertEquals(material3, materials.get(2));
        assertEquals(material2, materials.get(3));
        assertEquals(material1, materials.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Material> materials = dao.get(limit(2));

        assertEquals(2, materials.size());
        assertEquals(material1, materials.get(0));
        assertEquals(material2, materials.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Material> materials = dao.get(limit(2), offset(1));

        assertEquals(material2, materials.get(0));
        assertEquals(material3, materials.get(1));
    }

    @Test
    public void getActive() throws Exception
    {
        Material newMaterial = dao.create(
                Material.blueprint(randomString(), randomString(), randomString(), randomInt(0, 100), randomInt(0, 100), 11)
                                         );

        List<Material> active = dao.getActive();

        assertEquals(material2, active.get(0));
        assertEquals(material3, active.get(1));
        assertEquals(material4, active.get(2));
        assertEquals(material5, active.get(3));
        assertEquals(newMaterial, active.get(4));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(material3, dao.first(where(eq(ID, material3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String   number      = randomString();
        String   description = randomString();
        String   notes       = randomString();
        int      width       = randomInt(0, 100000);
        int      height      = randomInt(0, 100000);
        int      usage       = randomInt(0, 100000);
        Material actual      = dao.create(Material.blueprint(number, description, notes, width, height, usage));
        assertEquals(number, actual.getNumber());
        assertEquals(description, actual.getDescription());
        assertEquals(notes, actual.getNotes());
        assertEquals(width, actual.getWidth());
        assertEquals(height, actual.getHeight());
        assertEquals(usage, actual.getUsage());
    }

    @Test
    public void update() throws Exception
    {
        material1.setDescription(randomString());
        material1.setNotes(randomString());
        material1.setWidth(randomInt(0, 100000));
        material1.setHeight(randomInt(0, 100000));

        assertTrue(dao.update(material1));

        List<Material> actual = dao.get(where(eq(ID, material1.getId())));
        assertEquals(material1, actual.get(0));
    }
}