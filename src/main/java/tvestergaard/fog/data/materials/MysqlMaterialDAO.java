package tvestergaard.fog.data.materials;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.append;
import static tvestergaard.fog.data.constraints.Constraint.limit;

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
        final String         SQL       = generator.generate("SELECT * FROM materials", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                materials.add(createMaterial("materials", resultSet));

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
            final String SQL = "INSERT INTO materials (`number`, description, price, unit) " +
                    "VALUES (?, ?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getNumber());
                statement.setString(2, blueprint.getDescription());
                statement.setInt(3, blueprint.getPrice());
                statement.setInt(4, blueprint.getUnit());
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return new MaterialRecord(
                        generated.getInt(1),
                        blueprint.getNumber(),
                        blueprint.getDescription(),
                        blueprint.getPrice(),
                        blueprint.getUnit()
                );
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
                int updated = statement.executeUpdate();
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
}
