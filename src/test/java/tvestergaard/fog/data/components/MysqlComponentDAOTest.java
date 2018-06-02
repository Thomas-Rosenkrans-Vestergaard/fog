package tvestergaard.fog.data.components;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static tvestergaard.fog.Helpers.randomString;

public class MysqlComponentDAOTest
{

    private MysqlComponentDAO dao;

    @Before
    public void before() throws Exception
    {
        dao = new MysqlComponentDAO(TestDataSource.getSource());

        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("INSERT INTO categories (id, name) VALUES (1, 'cat')");
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("DELETE FROM component_definitions");
        connection.createStatement().executeUpdate("DELETE FROM categories");
    }

    @Test
    public void getReturnsNull() throws Exception
    {
        assertNull(dao.get(0));
    }

    @Test
    public void get() throws Exception
    {
        String expectedIdentifier = randomString();
        String expectedNotes      = randomString();

        ComponentDefinition create = dao.create(ComponentDefinitionBlueprint.from(expectedIdentifier, expectedNotes, 1));
        ComponentDefinition get    = dao.get(create.getId());

        assertEquals(expectedIdentifier, create.getIdentifier());
        assertEquals(expectedNotes, create.getNotes());
        assertEquals(1, create.getCategoryId());
        assertEquals("cat", create.getCategory().getName());
    }

    @Test
    public void create() throws Exception
    {
        String expectedIdentifier = randomString();
        String expectedNotes      = randomString();

        ComponentDefinition definition = dao.create(ComponentDefinitionBlueprint.from(expectedIdentifier, expectedNotes, 1));

        assertEquals(expectedIdentifier, definition.getIdentifier());
        assertEquals(expectedNotes, definition.getNotes());
        assertEquals(1, definition.getCategoryId());
        assertEquals("cat", definition.getCategory().getName());
    }

    @Test
    public void update() throws Exception
    {
        String expectedIdentifier = randomString();
        String expectedNotes      = randomString();

        String newNotes = randomString();

        ComponentDefinition definition = dao.create(ComponentDefinitionBlueprint.from(expectedIdentifier, expectedNotes, 1));
        definition.setNotes(newNotes);
        List<ComponentDefinition> args = new ArrayList<>();
        args.add(definition);
        dao.update(args);
        definition = dao.get(definition.getId());

        assertEquals(newNotes, definition.getNotes());
    }
}