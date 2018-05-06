package tvestergaard.fog.data.materials;

import org.mindrot.jbcrypt.BCrypt;

public class MysqlMaterialDAOTest
{

//    private static final MysqlDataSource  source = TestDataSource.getSource();
//    private static final MysqlMaterialDAO dao    = new MysqlMaterialDAO(source);
//
//    private Material material1;
//    private Material material2;
//    private Material material3;
//    private Material material4;
//    private Material material5;
//
//    @Before
//    public void before() throws Exception
//    {
//        material1 = dao.create(MaterialBlueprint.from("number1", "description1", 1, 6));
//        material2 = dao.create(MaterialBlueprint.from("number2", "description2", 2, 7));
//        material3 = dao.create(MaterialBlueprint.from("number3", "description3", 3, 8));
//        material4 = dao.create(MaterialBlueprint.from("number4", "description4", 4, 9));
//        material5 = dao.create(MaterialBlueprint.from("number5", "description5", 5, 10));
//    }
//
//    @After
//    public void after() throws Exception
//    {
//        Connection connection = TestDataSource.getSource().getConnection();
//        connection.createStatement().executeUpdate("DELETE FROM roofing_component_attribute_values");
//        connection.createStatement().executeUpdate("DELETE FROM roofing_component_attribute_definitions");
//        connection.createStatement().executeUpdate("DELETE FROM roofing_component_values");
//        connection.createStatement().executeUpdate("DELETE FROM roofing_component_definitions");
//        connection.createStatement().executeUpdate("DELETE FROM materials");
//    }
//
//    @Test
//    public void get() throws Exception
//    {
//        List<Material> materials = dao.get();
//
//        assertEquals(5, materials.size());
//        assertEquals(material1, materials.get(0));
//        assertEquals(material2, materials.get(1));
//        assertEquals(material3, materials.get(2));
//        assertEquals(material4, materials.get(3));
//        assertEquals(material5, materials.get(4));
//    }
//
//    @Test
//    public void getWhereEquals() throws Exception
//    {
//        List<Material> materials = dao.get(where(eq(ID, material1.getId())));
//
//        assertEquals(1, materials.size());
//        assertEquals(material1, materials.get(0));
//    }
//
//    @Test
//    public void getWhereLike() throws Exception
//    {
//        List<Material> materials = dao.get(where(like(DESCRIPTION, "description%")));
//
//        assertEquals(material1, materials.get(materials.size() - 5));
//        assertEquals(material2, materials.get(materials.size() - 4));
//        assertEquals(material3, materials.get(materials.size() - 3));
//        assertEquals(material4, materials.get(materials.size() - 2));
//        assertEquals(material5, materials.get(materials.size() - 1));
//    }
//
//    @Test
//    public void getOrderBy() throws Exception
//    {
//        List<Material> materials = dao.get(order(ID, desc()));
//
//        assertEquals(5, materials.size());
//        assertEquals(material5, materials.get(0));
//        assertEquals(material4, materials.get(1));
//        assertEquals(material3, materials.get(2));
//        assertEquals(material2, materials.get(3));
//        assertEquals(material1, materials.get(4));
//    }
//
//    @Test
//    public void getLimit() throws Exception
//    {
//        List<Material> materials = dao.get(limit(2));
//
//        assertEquals(2, materials.size());
//        assertEquals(material1, materials.get(0));
//        assertEquals(material2, materials.get(1));
//    }
//
//    @Test
//    public void getOffset() throws Exception
//    {
//        List<Material> materials = dao.get(limit(2), offset(1));
//
//        assertEquals(material2, materials.get(0));
//        assertEquals(material3, materials.get(1));
//    }
//
//    @Test
//    public void first() throws Exception
//    {
//        assertEquals(material3, dao.first(where(eq(ID, material3.getId()))));
//        assertNull(dao.first(where(eq(ID, -1))));
//    }
//
//    @Test
//    public void create() throws Exception
//    {
//        String   number      = randomString();
//        String   description = randomString();
//        int      price       = randomInt(0, 100000);
//        int      unit        = randomInt(0, 100000);
//        Material actual      = dao.create(MaterialBlueprint.from(number, description, price, unit));
//        assertEquals(number, actual.getNumber());
//        assertEquals(description, actual.getDescription());
//        assertEquals(price, actual.getPrice());
//        assertEquals(unit, actual.getUnit());
//    }
//
//    @Test
//    public void update() throws Exception
//    {
//        material1.setDescription(randomString());
//        material1.setPrice(randomInt(0, 10000));
//        material1.setUnit(randomInt(0, 100000));
//
//        assertTrue(dao.update(material1));
//
//        List<Material> actual = dao.get(where(eq(ID, material1.getId())));
//        assertEquals(material1, actual.get(0));
//    }
}