package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlCustomerDAO extends AbstractMysqlDAO implements CustomerDAO
{

    /**
     * The generator used to generate SQL for the {@link Constraint}s provided to this DAO.
     */
    private final StatementGenerator<CustomerColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the {@link Constraint}s provided to this DAO.
     */
    private final StatementBinder<CustomerColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlCustomerDAO}.
     *
     * @param source The source {@link MysqlCustomerDAO}.
     */
    public MysqlCustomerDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the customers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting customers.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public List<Customer> get(Constraint<CustomerColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Customer> customers = new ArrayList<>();
        final String         SQL       = generator.generate("SELECT * FROM customers", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                customers.add(createCustomer(resultSet));

            return customers;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first customer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first customer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Customer first(Constraint<CustomerColumn>... constraints) throws MysqlDataAccessException
    {
        List<Customer> customers = get(append(constraints, limit(1)));

        return customers.isEmpty() ? null : customers.get(0);
    }

    /**
     * Inserts a new customer into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The customer instance representing the newly created customer.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public Customer create(CustomerBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL =
                    "INSERT INTO customers (name, address, email, phone, password, active) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getName());
                statement.setString(2, blueprint.getAddress());
                statement.setString(3, blueprint.getEmail());
                statement.setString(4, blueprint.getPhone());
                statement.setString(5, blueprint.getPassword());
                statement.setBoolean(6, blueprint.isActive());
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return first(where(eq(CustomerColumn.ID, generated.getInt(1))));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code customer}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public boolean update(CustomerUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String SQL = "UPDATE customers SET name = ?, address = ?, email = ?, phone = ?," +
                    "password = ?, active = ? WHERE id = ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getName());
                statement.setString(2, updater.getAddress());
                statement.setString(3, updater.getEmail());
                statement.setString(4, updater.getPhone());
                statement.setString(5, updater.getPassword());
                statement.setBoolean(6, updater.isActive());
                statement.setInt(7, updater.getId());
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
