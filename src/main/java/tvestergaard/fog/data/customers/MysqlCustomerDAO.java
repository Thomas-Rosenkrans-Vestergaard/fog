package tvestergaard.fog.data.customers;

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
        try {
            final List<Customer> customers = new ArrayList<>();
            final String SQL = generator.generate("SELECT * FROM customers", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    customers.add(createCustomer(resultSet));

                return customers;
            }
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
        constraints = append(constraints, limit(1));

        try {
            final String SQL = generator.generate("SELECT * FROM customers", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.first())
                    return null;

                return createCustomer(resultSet);
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Inserts a new customer into the data storage.
     *
     * @param name          The name of the customer to create.
     * @param address       The address of the customer to create.
     * @param email
     * @param phone         The phone number of the customer to create.
     * @param password      The password of the customer to create.
     * @param contactMethod The preferred contact method of the customer to create.
     * @param active        Whether or not the customer can be applied to orders.     @return The customer instance
     *                      representing the newly created customer.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Customer create(String name,
                           String address,
                           String email,
                           String phone,
                           String password,
                           ContactMethod contactMethod,
                           boolean active) throws MysqlDataAccessException
    {
        try {
            final String SQL =
                    "INSERT INTO customers (name, address, email, phone, password, contact_method, active) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, address);
                statement.setString(3, email);
                statement.setString(4, phone);
                statement.setString(5, password);
                statement.setInt(6, contactMethod.id);
                statement.setBoolean(7, active);
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
     * @param customer The customer to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public boolean update(Customer customer) throws DataAccessException
    {
        try {
            final String SQL = "UPDATE customers SET name = ?, address = ?, email = ?, phone = ?," +
                    "password = ?, contact_method = ?, active = ? WHERE id = ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getAddress());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPhone());
                statement.setString(5, customer.getPassword());
                statement.setInt(6, customer.getContactMethod().id);
                statement.setBoolean(7, customer.isActive());
                statement.setInt(8, customer.getId());
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
