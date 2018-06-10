package tvestergaard.fog.data.materials;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.constraints.OrderDirection;
import tvestergaard.fog.data.materials.attributes.*;
import tvestergaard.fog.data.materials.categories.Category;
import tvestergaard.fog.data.materials.categories.CategoryRecord;

import java.sql.Connection;
import java.util.*;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.materials.MaterialColumn.DESCRIPTION;
import static tvestergaard.fog.data.materials.MaterialColumn.ID;

public class MysqlMaterialDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlMaterialDAO dao    = new MysqlMaterialDAO(source);

    private Category            category1;
    private AttributeDefinition attribute1;
    private AttributeDefinition attribute2;

    private Material material1;
    private Material material2;
    private Material material3;
    private Material material4;
    private Material material5;

    @Before
    public void before() throws Exception
    {

        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("INSERT INTO categories (id, name) VALUES (1, 'cat')");
        category1 = new CategoryRecord(1, "cat");

        connection.createStatement().executeUpdate("INSERT INTO attribute_definitions (id, category, data_type, name) VALUES (1, 1, 'INT', 'A')");
        connection.createStatement().executeUpdate("INSERT INTO attribute_definitions (id, category, data_type, name) VALUES (2, 1, 'STRING', 'B')");
        attribute1 = new DefaultAttributeDefinition(1, "A", DataType.INT);
        attribute2 = new DefaultAttributeDefinition(2, "B", DataType.STRING);

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new DefaultAttribute(attribute1, 123));
        attributes.add(new DefaultAttribute(attribute2, "string"));

        material1 = dao.create(MaterialBlueprint.from("number1", "description1", 1, 6, 1, attributes));
        material2 = dao.create(MaterialBlueprint.from("number2", "description2", 2, 7, 1, attributes));
        material3 = dao.create(MaterialBlueprint.from("number3", "description3", 3, 8, 1, attributes));
        material4 = dao.create(MaterialBlueprint.from("number4", "description4", 4, 9, 1, attributes));
        material5 = dao.create(MaterialBlueprint.from("number5", "description5", 5, 10, 1, attributes));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM attribute_values");
        connection.createStatement().executeUpdate("DELETE FROM attribute_definitions");
        connection.createStatement().executeUpdate("DELETE FROM materials");
        connection.createStatement().executeUpdate("DELETE FROM categories");
        connection.commit();
    }

    @Test
    public void get() throws Exception
    {
        List<Material> materials = dao.get();

        assertEquals(5, materials.size());
        assertEquals(material1, materials.get(4));
        assertEquals(material2, materials.get(3));
        assertEquals(material3, materials.get(2));
        assertEquals(material4, materials.get(1));
        assertEquals(material5, materials.get(0));
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

        assertEquals(material1, materials.get(4));
        assertEquals(material2, materials.get(3));
        assertEquals(material3, materials.get(2));
        assertEquals(material4, materials.get(1));
        assertEquals(material5, materials.get(0));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Material> materials = dao.get(order(ID, OrderDirection.DESC));

        assertEquals(5, materials.size());
        assertEquals(material5, materials.get(0));
        assertEquals(material4, materials.get(1));
        assertEquals(material3, materials.get(2));
        assertEquals(material2, materials.get(3));
        assertEquals(material1, materials.get(4));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(material1, dao.first(where(eq(ID, material1.getId()))));
        assertEquals(material2, dao.first(where(eq(ID, material2.getId()))));
        assertEquals(null, dao.first(where(eq(ID, 0))));
    }

    @Test
    public void create() throws Exception
    {
        String         number             = randomString();
        String         description        = randomString();
        int            price              = randomInt(0, 100000);
        int            unit               = randomInt(0, 100000);
        Set<Attribute> expectedAttributes = new HashSet<>();
        expectedAttributes.add(new DefaultAttribute(attribute1, 123));
        expectedAttributes.add(new DefaultAttribute(attribute2, "string"));

        Material actual = dao.create(MaterialBlueprint.from(number, description, price, unit, 1, expectedAttributes));
        assertEquals(number, actual.getNumber());
        assertEquals(description, actual.getDescription());
        assertEquals(price, actual.getPrice());
        assertEquals(unit, actual.getUnit());
        assertEquals(expectedAttributes, actual.getAttributes());
    }

    @Test
    public void update() throws Exception
    {
        String expectedDescription = randomString();
        int    expectedPrice       = randomInt(0, 100000);
        int    expectedUnit        = randomInt(0, 100000);

        material1.setDescription(expectedDescription);
        material1.setPrice(expectedPrice);
        material1.setUnit(expectedUnit);

        Material newMaterial = dao.update(material1);

        assertNotEquals(material1.getId(), newMaterial.getId()); // Ensure that a new material was created
        assertEquals(material1.getNumber(), newMaterial.getNumber());
        assertEquals(material1.getDescription(), expectedDescription);
        assertEquals(material1.getPrice(), expectedPrice);
        assertEquals(material1.getUnit(), expectedUnit);
        assertEquals(material1.isActive(), newMaterial.isActive());
        assertEquals(material1.getCategory(), newMaterial.getCategory());
        assertEquals(material1.getAttributes(), newMaterial.getAttributes());
    }

    @Test
    public void getAttributesFor() throws Exception
    {
        Set<AttributeDefinition> definitions = dao.getAttributesFor(1);

        assertTrue(definitions.contains(attribute1));
        assertTrue(definitions.contains(attribute2));
    }

    @Test
    public void getCategories() throws Exception
    {
        List<Category> categories = dao.getCategories();

        assertEquals(1, categories.size());
        assertEquals(category1, categories.get(0));
    }

    @Test
    public void getByCategory() throws Exception
    {
        Map<Integer, Collection<SimpleMaterial>> result = dao.getByCategory(1).asMap();

        assertTrue(result.containsKey(1));
        assertTrue(result.get(1).contains(material1));
        assertTrue(result.get(1).contains(material2));
    }
}