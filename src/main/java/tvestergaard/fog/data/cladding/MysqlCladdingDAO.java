package tvestergaard.fog.data.cladding;

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

public class MysqlCladdingDAO extends AbstractMysqlDAO implements CladdingDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlCladdingDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlCladdingDAO}.
     */
    public MysqlCladdingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the claddings in the data storage.
     * The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting claddings.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Cladding> get(Constraint<CladdingColumn>... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Cladding> floors = new ArrayList<>();
            final String         SQL    = generator.generate("SELECT * FROM claddings", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createCladding(resultSet));

                return floors;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first cladding matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first cladding matching the provided constraints. Returns {@code null} when no
     * constraints matches the provided constraints.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public Cladding first(Constraint<CladdingColumn>... constraints) throws MysqlDataAccessException
    {
        constraints = append(constraints, limit(1));

        try {
            final String SQL = generator.generate("SELECT * FROM claddings", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.first())
                    return null;

                return createCladding(resultSet);
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new cladding into the data storage.
     *
     * @param name                The name of the cladding to create.
     * @param description         The description of the cladding to create.
     * @param pricePerSquareMeter The price per square meter of cladding (in øre).
     * @param active              Whether or not the cladding can be applied to orders.
     * @return The cladding instance representing the newly created cladding.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public Cladding create(String name, String description, int pricePerSquareMeter, boolean active)
            throws MysqlDataAccessException
    {
        try {
            final String SQL        = "INSERT INTO claddings (name, description, price_per_square_meter, active) VALUES (?,?,?,?)";
            Connection   connection = getConnection();
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
                return new CladdingRecord(generated.getInt(1), name, description, pricePerSquareMeter, active);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param cladding The cladding to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public boolean update(Cladding cladding) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE claddings SET name = ?, description = ?, price_per_square_meter = ?, active = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, cladding.getName());
                statement.setString(2, cladding.getDescription());
                statement.setInt(3, cladding.getPricePerSquareMeter());
                statement.setBoolean(4, cladding.isActive());
                statement.setInt(5, cladding.getId());
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
