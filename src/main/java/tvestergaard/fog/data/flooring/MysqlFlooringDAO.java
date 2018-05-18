package tvestergaard.fog.data.flooring;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlFlooringDAO extends AbstractMysqlDAO implements FlooringDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<FlooringColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<FlooringColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlFlooringDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlFlooringDAO}.
     */
    public MysqlFlooringDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the floorings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting floorings.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public List<Flooring> get(Constraints<FlooringColumn> constraints) throws MysqlDataAccessException
    {
        final List<Flooring> floors = new ArrayList<>();
        final String SQL = generator.generate(
                "SELECT *, CONCAT_WS('.', name, description) as search FROM floorings", constraints);
        try (java.sql.PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                floors.add(createFlooring("floorings", resultSet));

            return floors;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first flooring matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first flooring matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public Flooring first(Constraints<FlooringColumn> constraints) throws MysqlDataAccessException
    {
        List<Flooring> floorings = get(constraints.limit(1));

        return floorings.isEmpty() ? null : floorings.get(0);
    }

    /**
     * Inserts a new flooring into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The flooring instance representing the newly created flooring.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Flooring create(FlooringBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL =
                    "INSERT INTO floorings (name, description, active) VALUES (?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getName());
                statement.setString(2, blueprint.getDescription());
                statement.setBoolean(3, blueprint.isActive());
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return new FlooringRecord(
                        generated.getInt(1),
                        blueprint.getName(),
                        blueprint.getDescription(),
                        blueprint.isActive());
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code flooring}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(FlooringUpdater updater) throws DataAccessException
    {
        try {
            final String SQL        = "UPDATE floorings SET name = ?, description = ?, active = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getName());
                statement.setString(2, updater.getDescription());
                statement.setBoolean(3, updater.isActive());
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
