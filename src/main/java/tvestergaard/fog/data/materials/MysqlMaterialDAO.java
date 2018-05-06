package tvestergaard.fog.data.materials;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.materials.categories.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.materials.MaterialColumn.ID;

public class MysqlMaterialDAO extends AbstractMysqlDAO implements MaterialDAO
{

    /**
     * The generator used to create SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder binder = new StatementBinder();

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
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting materials.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public List<Material> get(Constraint<MaterialColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Material> materials = new ArrayList<>();
        final String SQL = generator.generate("SELECT * FROM materials " +
                "INNER JOIN categories ON materials.category = categories.id", constraints);
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
     * Returns the first material matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first material matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public Material first(Constraint<MaterialColumn>... constraints) throws MysqlDataAccessException
    {
        List<Material> materials = get(append(constraints, limit(1)));

        return materials.isEmpty() ? null : materials.get(0);
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

                return first(where(eq(ID, generated.getInt(1))));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code material}.
     *
     * @param updater The material updater that contains the information necessary to create the material.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public boolean update(MaterialUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE materials SET description = ?, price = ?, unit = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getDescription());
                statement.setInt(2, updater.getPrice());
                statement.setInt(3, updater.getUnit());
                statement.setInt(4, updater.getId());
                statement.executeUpdate();

                String attributeSQL = "UPDATE attribute_values av SET av.`value` = ? WHERE av.attribute = ? AND av.material = ?";
                try (PreparedStatement attributeStatement = connection.prepareStatement(attributeSQL)) {
                    attributeStatement.setInt(3, updater.getId());
                    for (AttributeValue attributeValue : updater.getAttributes()) {
                        setAttributeValue(attributeStatement, 1, attributeValue);
                        attributeStatement.setInt(2, attributeValue.getDefinition().getId());
                        attributeStatement.executeUpdate();
                    }
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
}
