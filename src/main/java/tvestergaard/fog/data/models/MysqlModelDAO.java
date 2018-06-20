package tvestergaard.fog.data.models;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentConnection;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.materials.SimpleMaterial;
import tvestergaard.fog.data.materials.attributes.Attribute;
import tvestergaard.fog.data.materials.attributes.MysqlAttributeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlModelDAO extends AbstractMysqlDAO implements ModelDAO
{

    private final MysqlAttributeDAO attributeDAO;

    /**
     * Creates a new {@link MysqlModelDAO}.
     *
     * @param source The source to act upon.
     */
    public MysqlModelDAO(MysqlDataSource source)
    {
        super(source);
        this.attributeDAO = new MysqlAttributeDAO(source);
    }

    /**
     * Returns a complete list of the garage models available.
     *
     * @return The complete list.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Model> get() throws MysqlDataAccessException
    {
        List<Model> models = new ArrayList<>();
        String      SQL    = "SELECT * FROM models";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                models.add(createModel(resultSet, "models"));

            return models;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the model with the provided id.
     *
     * @param id The id of the model to find.
     * @return The object representing the model with the provided id.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Model get(int id) throws MysqlDataAccessException
    {
        String SQL = "SELECT * FROM models WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.first())
                return null;

            return createModel(resultSet, "models");
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates a model in the application.
     *
     * @param updater    The updater containing the information needed to update the model.
     * @param components The new components of the model.
     * @return {@code true} if the model was successfully updated.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(ModelUpdater updater, List<ComponentConnection> components) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE models SET name = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getName());
                statement.setInt(2, updater.getId());

                int updated = statement.executeUpdate();

                String deleteSQL = "DELETE FROM component_values WHERE definition IN (SELECT definition FROM model_component_definitions mcd WHERE mcd.model = ?)";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {
                    deleteStatement.setInt(1, updater.getId());
                    deleteStatement.executeUpdate();
                }

                String insertSQL = "INSERT INTO component_values (definition, material) VALUES (?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                    for (ComponentConnection componentValue : components) {
                        insertStatement.setInt(1, componentValue.getDefinitionId());
                        insertStatement.setInt(2, componentValue.getMaterialId());
                        updated += insertStatement.executeUpdate();
                    }
                }

                connection.commit();
                return updated != 0;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the component definitions for the provided garage model.
     *
     * @param model The garage model to return the component definitions for.
     * @return The component definitions for the garage skeleton.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<ComponentDefinition> getComponentDefinitions(int model) throws DataAccessException
    {
        List<ComponentDefinition> definitions = new ArrayList<>();

        String SQL = "SELECT * FROM model_component_definitions mcd " +
                "INNER JOIN component_definitions cd ON mcd.definition = cd.id " +
                "INNER JOIN categories ON categories.id = cd.category " +
                "WHERE mcd.model = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, model);
            ResultSet componentResults = statement.executeQuery();
            while (componentResults.next())
                definitions.add(createComponentDefinition(componentResults, "cd", "categories"));

            return definitions;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the active components for the provided garage model.
     *
     * @param model The garage model to return the component of.
     * @return The list of the active components for the garage skeleton.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Component> getComponents(int model) throws DataAccessException
    {
        List<Component> components = new ArrayList<>();

        String SQL = "SELECT * FROM component_values cv " +
                "INNER JOIN component_definitions cd ON cv.definition = cd.id " +
                "INNER JOIN model_component_definitions mcd ON cd.id = mcd.definition " +
                "INNER JOIN materials ON cv.material = materials.id " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "WHERE mcd.model = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, model);
            ResultSet resultSet = statement.executeQuery();
            String attributeSQL = "SELECT * FROM attribute_definitions ad " +
                    "INNER JOIN attribute_values av ON ad.id = av.attribute " +
                    "WHERE av.material = ?";

            try (PreparedStatement attributeStatement = getConnection().prepareStatement(attributeSQL)) {
                while (resultSet.next()) {
                    attributeStatement.setInt(1, resultSet.getInt("materials.id"));
                    ResultSet attributes = attributeStatement.executeQuery();
                    components.add(createComponent(resultSet, "cd", "cv", "materials", "categories", attributes, "ad", "av"));
                }
            }

            return components;

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the materials that can be chosen for garage model components.
     *
     * @param model The garage model to return the material choices for.
     * @return Returns the material choices for the components of the garage skeleton. The material is then mapped to the
     * id of the component.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Multimap<Integer, SimpleMaterial> getComponentMaterials(int model) throws DataAccessException
    {
        Multimap<Integer, SimpleMaterial> results = ArrayListMultimap.create();

        String SQL = "SELECT * FROM materials " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "INNER JOIN component_definitions cd ON cd.category = categories.id " +
                "INNER JOIN model_component_definitions mcd ON mcd.definition = cd.id";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                results.put(resultSet.getInt("cd.id"), createSimpleMaterial(resultSet, "materials", "categories"));

            return results;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
