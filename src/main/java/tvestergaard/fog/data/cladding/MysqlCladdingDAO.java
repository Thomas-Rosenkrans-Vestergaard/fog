package tvestergaard.fog.data.cladding;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
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
     * The generator used to generate SQL for the {@link Constraint}s provided to this DAO.
     */
    private final StatementGenerator generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the {@link Constraint}s provided to this DAO.
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
     * Returns the {@link Cladding}s in the data storage.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Cladding}s.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Cladding> get(Constraint... constraints) throws MysqlDataAccessException
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
     * Returns the first cladding matching the provided {@link Constraint}s.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first {@link Cladding} matching the provided {@link Constraint}s. Returns {@code null} when no
     * {@link Constraint}s matches the provided {@link Constraint}s.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    @Override public Cladding first(Constraint... constraints) throws DataAccessException
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
     * Inserts a new {@link Cladding} into the data storage.
     *
     * @param name                The name of the {@link Cladding} to create.
     * @param description         The description of the {@link Cladding} to create.
     * @param pricePerSquareMeter The price per square meter of {@link Cladding} (in øre).
     * @param active              Whether or not the {@link Cladding} can be applied to orders.
     * @return The {@link Cladding} instance representing the newly created {@link Cladding}.
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
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param cladding The {@link Cladding} to update the entity in the data storage to.
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
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
