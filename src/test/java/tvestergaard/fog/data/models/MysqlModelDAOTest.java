package tvestergaard.fog.data.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.components.ComponentDAO;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentDefinitionBlueprint;
import tvestergaard.fog.data.components.MysqlComponentDAO;
import tvestergaard.fog.data.materials.Category;
import tvestergaard.fog.data.materials.categories.CategoryRecord;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static tvestergaard.fog.Helpers.randomString;

public class MysqlModelDAOTest
{

    private final ModelDAO     dao          = new MysqlModelDAO(TestDataSource.getSource());
    private final ComponentDAO componentDAO = new MysqlComponentDAO(TestDataSource.getSource());

    private Category category1;

    private ComponentDefinition component1;
    private ComponentDefinition component2;
    private ComponentDefinition component3;

    private Model model1;
    private Model model2;
    private Model model3;

    @Before
    public void before() throws Exception
    {
        Connection c = TestDataSource.getSource().getConnection();

        c.createStatement().executeUpdate("INSERT INTO models (id, name) VALUES (1, 'A')");
        c.createStatement().executeUpdate("INSERT INTO models (id, name) VALUES (2, 'B')");
        c.createStatement().executeUpdate("INSERT INTO models (id, name) VALUES (3, 'C')");

        c.createStatement().executeUpdate("INSERT INTO categories (id, name) VALUES (1, 'cat')");
        category1 = new CategoryRecord(1, "cat");

        component1 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));
        component2 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));
        component3 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));

        c.createStatement().executeUpdate("INSERT INTO model_component_definitions (model, definition) VALUES (1, " + component1.getId() + ")");
        c.createStatement().executeUpdate("INSERT INTO model_component_definitions (model, definition) VALUES (1, " + component2.getId() + ")");
        c.createStatement().executeUpdate("INSERT INTO model_component_definitions (model, definition) VALUES (1, " + component3.getId() + ")");

        this.model1 = new ModelRecord(1, "A");
        this.model2 = new ModelRecord(2, "B");
        this.model3 = new ModelRecord(3, "C");
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("DELETE FROM model_component_definitions");
        connection.createStatement().executeUpdate("DELETE FROM component_definitions");
        connection.createStatement().executeUpdate("DELETE FROM models");
        connection.createStatement().executeUpdate("DELETE FROM categories");

        connection.commit();
    }

    @Test
    public void get() throws Exception
    {
        List<Model> models = dao.get();

        assertEquals(3, models.size());
        assertEquals(model1, models.get(0));
        assertEquals(model2, models.get(1));
        assertEquals(model3, models.get(2));
    }

    @Test
    public void getById() throws Exception
    {
        assertEquals(model2, dao.get(2));
    }

    @Test
    public void getByIdReturnsNull() throws Exception
    {
        assertNull(dao.get(1000));
    }

    @Test
    public void update() throws Exception
    {
        String expectedName = randomString();
        model1.setName(expectedName);

        dao.update(model1, new ArrayList<>());

        Model actual = dao.get(model1.getId());
        assertEquals(model1, actual);
    }

    @Test
    public void getComponentDefinitions() throws Exception
    {
        List<ComponentDefinition> definitions = dao.getComponentDefinitions(1);

        assertEquals(3, definitions.size());
        assertEquals(component1, definitions.get(0));
        assertEquals(component2, definitions.get(1));
        assertEquals(component3, definitions.get(2));
    }

    @Test
    public void getComponents() throws Exception
    {
    }

    @Test
    public void getMaterialChoices() throws Exception
    {
    }
}