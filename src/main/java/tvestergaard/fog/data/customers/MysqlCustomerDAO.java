package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlCustomerDAO extends AbstractMysqlDAO implements CustomerDAO
{

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
     * Returns a complete list of the {@link Customer}s in the system.
     *
     * @return The complete list.
     * @throws MysqlDataAccessException When an exception occurs during the operation.
     */
    @Override public List<Customer> get() throws MysqlDataAccessException
    {
        try {
            final List<Customer> customers = new ArrayList<>();
            final String         SQL       = "SELECT * FROM customers";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    customers.add(createCustomer(resultSet));

                return customers;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}