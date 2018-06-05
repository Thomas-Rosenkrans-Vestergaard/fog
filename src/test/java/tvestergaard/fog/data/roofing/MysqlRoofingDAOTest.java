package tvestergaard.fog.data.roofing;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.components.*;
import tvestergaard.fog.data.constraints.OrderDirection;
import tvestergaard.fog.data.materials.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;
import static tvestergaard.fog.data.roofing.RoofingColumn.NAME;

public class MysqlRoofingDAOTest
{

    private static final MysqlDataSource   source       = TestDataSource.getSource();
    private static final MysqlMaterialDAO  materialDAO  = new MysqlMaterialDAO(source);
    private static final MysqlComponentDAO componentDAO = new MysqlComponentDAO(source);
    private static final MysqlRoofingDAO   dao          = new MysqlRoofingDAO(source);

    private Category category1;

    private ComponentDefinition component1;
    private ComponentDefinition component2;
    private ComponentDefinition component3;

    private Material material1;

    private Roofing roofing1;
    private Roofing roofing2;
    private Roofing roofing3;
    private Roofing roofing4;
    private Roofing roofing5;

    @Before
    public void createData() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("INSERT INTO categories (id, name) VALUES (1, 'cat')");
        category1 = new CategoryRecord(1, "cat");

        component1 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));
        component2 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));
        component3 = componentDAO.create(ComponentDefinitionBlueprint.from(randomString(), randomString(), 1));

        connection.createStatement().executeUpdate("INSERT INTO roofing_component_definitions (roofing_type, definition) VALUES ('TILED', " + component1.getId() + ")");
        connection.createStatement().executeUpdate("INSERT INTO roofing_component_definitions (roofing_type, definition) VALUES ('TILED', " + component2.getId() + ")");
        connection.createStatement().executeUpdate("INSERT INTO roofing_component_definitions (roofing_type, definition) VALUES ('TILED', " + component3.getId() + ")");

        material1 = materialDAO.create(MaterialBlueprint.from(randomString(), randomString(), randomInt(0, 10000), randomInt(0, 10000), 1, new HashSet<>()));

        roofing1 = dao.create(RoofingBlueprint.from("name1", "description1", true, RoofingType.TILED), new ArrayList<>());
        roofing2 = dao.create(RoofingBlueprint.from("name2", "description2", false, RoofingType.TILED), new ArrayList<>());
        roofing3 = dao.create(RoofingBlueprint.from("name3", "description3", true, RoofingType.TILED), new ArrayList<>());
        roofing4 = dao.create(RoofingBlueprint.from("name4", "description4", false, RoofingType.TILED), new ArrayList<>());
        roofing5 = dao.create(RoofingBlueprint.from("name5", "description5", true, RoofingType.TILED), new ArrayList<>());
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM roofing_component_values");
        connection.createStatement().executeUpdate("DELETE FROM roofing_component_definitions");
        connection.createStatement().executeUpdate("DELETE FROM component_values");
        connection.createStatement().executeUpdate("DELETE FROM component_definitions");
        connection.createStatement().executeUpdate("DELETE FROM roofings");
        connection.createStatement().executeUpdate("DELETE FROM materials");
        connection.createStatement().executeUpdate("DELETE FROM categories");

        connection.commit();
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
        List<Roofing> roofings = dao.get(order(RoofingColumn.NAME, OrderDirection.DESC));

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
        List<Roofing> roofings = dao.get(limit(RoofingColumn.class, 2));

        assertEquals(2, roofings.size());
        assertEquals(roofing1, roofings.get(0));
        assertEquals(roofing2, roofings.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Roofing> roofings = dao.get(limit(RoofingColumn.class, 2).offset(1));

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
        String                    name                 = "some_random_name";
        String                    description          = "some_random_description";
        boolean                   active               = false;
        RoofingBlueprint          blueprint            = RoofingBlueprint.from(name, description, active, RoofingType.TILED);
        List<ComponentConnection> componentConnections = new ArrayList<>();

        componentConnections.add(ComponentConnection.from(component3.getId(), material1.getId()));
        componentConnections.add(ComponentConnection.from(component1.getId(), material1.getId()));
        componentConnections.add(ComponentConnection.from(component2.getId(), material1.getId()));

        Roofing actual = dao.create(blueprint, componentConnections);

        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        assertEquals(active, actual.isActive());
        assertEquals(RoofingType.TILED, actual.getType());

        List<Component> components = dao.getComponents(actual.getId());
        assertEquals(componentConnections.get(0).getDefinitionId(), components.get(0).getDefinitionId());
        assertEquals(componentConnections.get(0).getDefinitionId(), components.get(0).getDefinition().getId());
        assertEquals(componentConnections.get(1).getDefinitionId(), components.get(1).getDefinitionId());
        assertEquals(componentConnections.get(1).getDefinitionId(), components.get(1).getDefinition().getId());
        assertEquals(componentConnections.get(2).getDefinitionId(), components.get(2).getDefinitionId());
        assertEquals(componentConnections.get(2).getDefinitionId(), components.get(2).getDefinition().getId());
    }

    @Test
    public void update() throws Exception
    {
        roofing1.setName(randomString());
        roofing1.setDescription(randomString());
        roofing1.setActive(true);

        assertTrue(dao.update(roofing1, new ArrayList<>()));

        List<Roofing> actual = dao.get(where(eq(ID, roofing1.getId())));
        assertEquals(roofing1, actual.get(0));
    }

    @Test
    public void getComponentDefinitions() throws Exception
    {
        List<ComponentDefinition> definitions = dao.getComponentDefinitions(RoofingType.TILED);

        assertEquals(3, definitions.size());
        assertEquals(component1, definitions.get(0));
        assertEquals(component2, definitions.get(1));
        assertEquals(component3, definitions.get(2));
    }
}