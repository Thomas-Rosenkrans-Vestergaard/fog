package tvestergaard.fog.data.roofing;

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

public class MysqlRoofingDAO extends AbstractMysqlDAO implements RoofingDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<RoofingColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<RoofingColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlRoofingDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlRoofingDAO}.
     */
    public MysqlRoofingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting roofings.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public List<Roofing> get(Constraint<RoofingColumn>... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Roofing> roofings = new ArrayList<>();
            final String SQL = generator.generate("SELECT * FROM roofings", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    roofings.add(createRoofing(resultSet));

                return roofings;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Roofing first(Constraint<RoofingColumn>... constraints) throws MysqlDataAccessException
    {
        constraints = append(constraints, limit(1));

        try {
            final String SQL = generator.generate("SELECT * FROM roofings", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.first())
                    return null;

                return createRoofing(resultSet);
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param name                The name of the roofing to create.
     * @param description         The description of the roofing to create.
     * @param minimumSlope        The minimum slope at which the roofing to create can be laid.
     * @param maximumSlope        The maximum slope at which the roofing to create can be laid.
     * @param pricePerSquareMeter The price per square meter of roofing (in øre).
     * @param active              Whether or not the roofing can be applied to orders.
     * @return The roofing instance representing the newly created roofing.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Roofing create(String name,
                          String description,
                          int minimumSlope,
                          int maximumSlope,
                          int pricePerSquareMeter,
                          boolean active) throws MysqlDataAccessException
    {
        try {
            final String SQL = "INSERT INTO roofings " +
                    "(name, description, minimum_slope, maximum_slope, price_per_square_meter, active) VALUES (?,?,?," +
                    "?,?,?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, minimumSlope);
                statement.setInt(4, maximumSlope);
                statement.setInt(5, pricePerSquareMeter);
                statement.setBoolean(6, active);
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return new RoofingRecord(generated.getInt(1), name, description, minimumSlope, maximumSlope,
                        pricePerSquareMeter, active);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param roofing The roofing to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public boolean update(Roofing roofing) throws MysqlDataAccessException
    {
        try {
            final String SQL = "UPDATE roofings SET name = ?, description = ?, minimum_slope = ?, maximum_slope = ?," +
                    " price_per_square_meter = ?, active = ? WHERE id = ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, roofing.getName());
                statement.setString(2, roofing.getDescription());
                statement.setInt(3, roofing.getMinimumSlope());
                statement.setInt(4, roofing.getMaximumSlope());
                statement.setInt(5, roofing.getPricePerSquareMeter());
                statement.setBoolean(6, roofing.isActive());
                statement.setInt(7, roofing.getId());
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
