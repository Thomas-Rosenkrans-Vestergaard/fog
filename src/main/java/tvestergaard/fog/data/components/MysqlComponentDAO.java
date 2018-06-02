package tvestergaard.fog.data.components;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.*;
import java.util.List;

public class MysqlComponentDAO extends AbstractMysqlDAO implements ComponentDAO
{

    /**
     * Creates a new {@link MysqlComponentDAO}.
     *
     * @param source The data source to query.
     */
    public MysqlComponentDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Retrieves the component definition with the provided id.
     *
     * @param id The id of the component definition to retrieve.
     * @return The object representing the retrieved component definition. Returns {@code null} when a component definition
     * with the provided id could not be found.
     * @throws MysqlDataAccessException When a data storage exception occurs during the operation.
     */
    @Override public ComponentDefinition get(int id) throws MysqlDataAccessException
    {
        try {

            String SQL = "SELECT * FROM component_definitions cd INNER JOIN categories ON categories.id = cd.category WHERE cd.id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.first())
                    return null;

                return createComponentDefinition(resultSet, "cd", "categories");
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new component from the provided blueprint.
     *
     * @param blueprint The blueprint to create the component from.
     * @return The object representing the inserted component.
     * @throws MysqlDataAccessException When a data storage exception occurs during the operation.
     */
    @Override public ComponentDefinition create(ComponentDefinitionBlueprint blueprint) throws MysqlDataAccessException
    {
        try {

            Connection connection = getConnection();

            String SQL = "INSERT INTO component_definitions (identifier, notes, category) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getIdentifier());
                statement.setString(2, blueprint.getNotes());
                statement.setInt(3, blueprint.getCategoryId());
                statement.executeUpdate();
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();

                return get(generated.getInt(1));
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the provided component definitions.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(List<ComponentDefinition> definitions) throws MysqlDataAccessException
    {
        try {

            String     SQL        = "UPDATE component_definitions cd SET cd.notes = ? WHERE cd.id = ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (ComponentDefinition definition : definitions) {
                    statement.setString(1, definition.getNotes());
                    statement.setInt(2, definition.getId());
                    statement.executeUpdate();
                }

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
