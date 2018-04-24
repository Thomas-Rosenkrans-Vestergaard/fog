package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerRecord;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingRecord;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
{

    /**
     * Creates a new {@link MysqlOrderDAO}.
     *
     * @param source The source acted upon.
     */
    public MysqlOrderDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a complete list of the {@link Order}s in the system.
     *
     * @return The complete list of the {@link Order}.
     * @throws MysqlDataAccessException When an exception occurs during the operation.
     */
    @Override public List<Order> getOrders() throws MysqlDataAccessException
    {
        try {
            final List<Order> orders = new ArrayList<>();
            final String SQL = "SELECT * FROM orders " +
                               "INNER JOIN customers ON orders.customer = customers.id " +
                               "INNER JOIN claddings ON orders.cladding = claddings.id " +
                               "INNER JOIN roofings ON orders.roofing = roofings.id " +
                               "INNER JOIN shed ON orders.shed = sheds.id";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    orders.add(createOrder(resultSet));

                return orders;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

}
