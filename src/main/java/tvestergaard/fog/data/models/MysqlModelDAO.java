package tvestergaard.fog.data.models;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.materials.SimpleMaterial;
import tvestergaard.fog.data.roofing.Component;
import tvestergaard.fog.data.roofing.ComponentDefinition;
import tvestergaard.fog.data.roofing.ComponentReference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlModelDAO extends AbstractMysqlDAO implements ModelDAO
{

    public MysqlModelDAO(MysqlDataSource source)
    {
        super(source);
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
        String      SQL    = "SELECT * FROM garage_models gm";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                models.add(createModel(resultSet, "gm"));

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
        String SQL = "SELECT * FROM garage_models gm WHERE gm.id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.first())
                return null;

            return createModel(resultSet, "gm");
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
    @Override public boolean update(ModelUpdater updater, List<ComponentReference> components) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE garage_models SET name = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getName());
                statement.setInt(2, updater.getId());

                int updated = statement.executeUpdate();

                String componentSQL = "UPDATE component_values cv SET material = ? " +
                        "WHERE definition = ? " +
                        "AND id IN (SELECT component FROM skeleton_component_values scv " +
                        "INNER JOIN skeleton_component_definitions scd ON scv.definition = scd.id WHERE scd.model = ?)";
                try (PreparedStatement componentStatement = connection.prepareStatement(componentSQL)) {
                    componentStatement.setInt(3, updater.getId());
                    for (ComponentReference component : components) {
                        componentStatement.setInt(1, component.getMaterialId());
                        componentStatement.setInt(2, component.getDefinitionId());
                        updated += componentStatement.executeUpdate();
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

        String SQL = "SELECT * FROM skeleton_component_definitions scd " +
                "INNER JOIN component_definitions cd ON scd.definition = cd.id " +
                "INNER JOIN categories ON categories.id = cd.category";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet componentResults = statement.executeQuery();
            while (componentResults.next())
                definitions.add(createComponentDefinition("cd", componentResults, "categories"));

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

        String SQL = "SELECT * FROM skeleton_component_values scv " +
                "INNER JOIN component_values cv ON scv.component = cv.id " +
                "INNER JOIN component_definitions cd ON cv.definition = cd.id " +
                "INNER JOIN materials ON cv.material = materials.id " +
                "INNER JOIN categories ON materials.category = categories.id";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            String attributeSQL = "SELECT * FROM attribute_definitions ad " +
                    "INNER JOIN attribute_values av ON ad.id = av.attribute " +
                    "WHERE av.material = ?";
            try (PreparedStatement attributeStatement = getConnection().prepareStatement(attributeSQL)) {
                while (resultSet.next()) {
                    attributeStatement.setInt(1, resultSet.getInt("materials.id"));
                    ResultSet attributes = attributeStatement.executeQuery();
                    components.add(createComponent("cd", "materials", "categories", resultSet, "ad", "av", attributes));
                }
            }

            return components;

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the material choices for the provided garage model.
     *
     * @param model The garage model to return the material choices for.
     * @return Returns the material choices for the components of the garage skeleton.The material is then mapped to the
     * id of the component.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Multimap<Integer, SimpleMaterial> getMaterialChoices(int model) throws DataAccessException
    {
        Multimap<Integer, SimpleMaterial> results = ArrayListMultimap.create();

        String SQL = "SELECT * FROM materials " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "INNER JOIN component_definitions cd ON cd.category = categories.id " +
                "INNER JOIN skeleton_component_definitions scd ON scd.definition = cd.id " +
                "GROUP BY materials.id";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                results.put(resultSet.getInt("cd.id"), createSimpleMaterial("materials", "categories", resultSet));

            return results;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
