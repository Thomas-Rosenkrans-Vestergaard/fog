package tvestergaard.fog.data.materials;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.materials.attributes.AttributeDefinition;
import tvestergaard.fog.data.materials.attributes.AttributeValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MysqlMaterialDAO extends AbstractMysqlDAO implements MaterialDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<MaterialColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<MaterialColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlMaterialDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlMaterialDAO}.
     */
    public MysqlMaterialDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the materials in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the results from the query.
     * @return The complete list of the materials in the data storage.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Material> get(Constraints<MaterialColumn> constraints) throws MysqlDataAccessException
    {
        constraints = constraints == null ? new Constraints<>() : constraints;
        constraints.groupBy(MaterialColumn.NUMBER);
        constraints.order("max(materials.id) DESC");

        final List<Material> materials = new ArrayList<>();

        final String SQL = generator.generate("SELECT * FROM materials INNER JOIN categories ON materials.category = categories.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            String attributeSQL = "SELECT * FROM attribute_definitions ad " +
                    "INNER JOIN attribute_values av ON ad.id = av.attribute " +
                    "WHERE av.material = ?";
            try (PreparedStatement attributeStatement = getConnection().prepareStatement(attributeSQL)) {
                while (resultSet.next()) {
                    attributeStatement.setInt(1, resultSet.getInt("materials.id"));
                    ResultSet attributes = attributeStatement.executeQuery();
                    materials.add(createMaterial("materials", "categories", resultSet, "ad", "av", attributes));
                }
            }

            return materials;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the material with the provided id.
     *
     * @param id The id of the material to return.
     * @return The material with the provided id. {@code null} in case a material with the provided id does not exist.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Material get(int id) throws MysqlDataAccessException
    {
        final String SQL = "SELECT * FROM materials " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "WHERE materials.id = ? " +
                "GROUP BY materials.number " +
                "ORDER BY max(materials.id) DESC ";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            String attributeSQL = "SELECT * FROM attribute_definitions ad " +
                    "INNER JOIN attribute_values av ON ad.id = av.attribute " +
                    "WHERE av.material = ?";

            if (!resultSet.first())
                return null;

            try (PreparedStatement attributeStatement = getConnection().prepareStatement(attributeSQL)) {
                attributeStatement.setInt(1, resultSet.getInt("materials.id"));
                ResultSet attributes = attributeStatement.executeQuery();
                return createMaterial("materials", "categories", resultSet, "ad", "av", attributes);
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new material into the data storage.
     *
     * @param blueprint The material blueprint that contains the information necessary to create the material.
     * @return The material instance representing the newly created material.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Material create(MaterialBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL = "INSERT INTO materials (`number`, description, price, unit, category) " +
                    "VALUES (?, ?, ?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getNumber());
                statement.setString(2, blueprint.getDescription());
                statement.setInt(3, blueprint.getPrice());
                statement.setInt(4, blueprint.getUnit());
                statement.setInt(5, blueprint.getCategoryId());
                int updated = statement.executeUpdate();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                int    materialId   = generated.getInt(1);
                String attributeSQL = "INSERT INTO attribute_values (attribute, material, `value`) VALUES (?, ?, ?)";
                try (PreparedStatement attributeStatement = connection.prepareStatement(attributeSQL)) {
                    attributeStatement.setInt(2, materialId);
                    for (AttributeValue attribute : blueprint.getAttributes()) {
                        attributeStatement.setInt(1, attribute.getDefinition().getId());
                        setAttributeValue(attributeStatement, 3, attribute);
                        attributeStatement.executeUpdate();
                    }
                }

                connection.commit();

                return get(generated.getInt(1));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new material into the data storage, replacing the provided previous material.
     *
     * @param updater The blueprint containing information about the material to update.
     * @return The material instance representing the newly created material.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Material update(MaterialUpdater updater) throws DataAccessException
    {
        try {

            Connection connection = getConnection();

            String deactivateSQL = "UPDATE materials SET active = b'0' WHERE number = ?";
            try (PreparedStatement deactivateStatement = connection.prepareStatement(deactivateSQL)) {
                deactivateStatement.setString(1, updater.getNumber());
                deactivateStatement.executeUpdate();
            }

            String insert = "INSERT INTO materials (`number`, description, price, unit, category) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, updater.getNumber());
                statement.setString(2, updater.getDescription());
                statement.setInt(3, updater.getPrice());
                statement.setInt(4, updater.getUnit());
                statement.setInt(5, updater.getCategoryId());
                int updated = statement.executeUpdate();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                int    materialId   = generated.getInt(1);
                String attributeSQL = "INSERT INTO attribute_values (attribute, material, `value`) VALUES (?, ?, ?)";
                try (PreparedStatement attributeStatement = connection.prepareStatement(attributeSQL)) {
                    attributeStatement.setInt(2, materialId);
                    for (AttributeValue attribute : updater.getAttributes()) {
                        attributeStatement.setInt(1, attribute.getDefinition().getId());
                        setAttributeValue(attributeStatement, 3, attribute);
                        attributeStatement.executeUpdate();
                    }

                    String componentUpdateSQL = "UPDATE component_values cv SET cv.material = ? WHERE cv.material = ?";
                    try (PreparedStatement componentUpdateStatement = connection.prepareStatement(componentUpdateSQL)) {
                        componentUpdateStatement.setInt(1, materialId);
                        componentUpdateStatement.setInt(2, updater.getId());
                        componentUpdateStatement.executeUpdate();
                    }
                }

                connection.commit();

                return get(generated.getInt(1));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns a list of the attributes required for the provided category id.
     *
     * @param category The category id to return the required attributes for.
     * @return The list of the attributes required for the provided category id.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public Set<AttributeDefinition> getAttributesFor(int category) throws MysqlDataAccessException
    {
        Set<AttributeDefinition> result = new HashSet<>();
        String                   SQL    = "SELECT * FROM attribute_definitions ad WHERE ad.category = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, category);
            ResultSet attributes = statement.executeQuery();
            while (attributes.next())
                result.add(createAttributeDefinition("ad", attributes));

            return result;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the complete list of the categories in the application.
     *
     * @return The complete list of the categories in the application.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Category> getCategories() throws MysqlDataAccessException
    {
        List<Category> result = new ArrayList<>();
        String         SQL    = "SELECT * FROM categories";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                result.add(createCategory("categories", resultSet));

            return result;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns a complete list of the materials in the provided categories.
     *
     * @param categories The categories to return the materials from.
     * @return The materials in the provided categories, mapped to that category in the multimap.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Multimap<Integer, SimpleMaterial> getByCategory(int... categories) throws MysqlDataAccessException
    {
        Multimap<Integer, SimpleMaterial> map = ArrayListMultimap.create();
        String SQL = "SELECT * FROM materials " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "WHERE materials.active = b'1' && category IN (" + createIn(categories.length) + ")";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            for (int x = 0; x < categories.length; x++)
                statement.setInt(x + 1, categories[x]);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                map.put(resultSet.getInt("materials.category"),
                        createSimpleMaterial("materials", "categories", resultSet));

            return map;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
