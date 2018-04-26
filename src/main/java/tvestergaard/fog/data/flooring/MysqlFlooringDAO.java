package tvestergaard.fog.data.flooring;

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
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public List<Flooring> get(Constraint<FlooringColumn>... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Flooring> floors = new ArrayList<>();
            final String SQL = generator.generate("SELECT floorings.* FROM floorings", constraints);
            try (java.sql.PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createFlooring(resultSet));

                return floors;
            }
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
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Flooring first(Constraint<FlooringColumn>... constraints) throws MysqlDataAccessException
    {
        constraints = append(constraints, limit(1));

        try {
            final String SQL = generator.generate("SELECT * FROM floorings", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.first())
                    return null;

                return createFlooring(resultSet);
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new flooring into the data storage.
     *
     * @param name                The name of the flooring to create.
     * @param description         The description of the flooring to create.
     * @param pricePerSquareMeter The price per square meter of flooring (in øre).
     * @param active              Whether or not the flooring can be applied to orders.
     * @return The flooring instance representing the newly created flooring.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Flooring create(String name, String description, int pricePerSquareMeter, boolean active)
            throws MysqlDataAccessException
    {
        try {
            final String SQL =
                    "INSERT INTO floorings (name, description, price_per_square_meter, active) VALUES (?,?,?,?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, pricePerSquareMeter);
                statement.setBoolean(4, active);
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return new FlooringRecord(generated.getInt(1), name, description, pricePerSquareMeter, active);
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
     * @param flooring The flooring to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public boolean update(Flooring flooring) throws MysqlDataAccessException
    {
        try {
            final String SQL =
                    "UPDATE floorings SET name = ?, description = ?, price_per_square_meter = ?, active = ? WHERE id " +
                            "= ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, flooring.getName());
                statement.setString(2, flooring.getDescription());
                statement.setInt(3, flooring.getPricePerSquareMeter());
                statement.setBoolean(4, flooring.isActive());
                statement.setInt(5, flooring.getId());
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
