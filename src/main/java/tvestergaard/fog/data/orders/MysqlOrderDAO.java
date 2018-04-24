package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.contraints.Constraint;
import tvestergaard.fog.data.contraints.StatementBinder;
import tvestergaard.fog.data.contraints.StatementGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
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
     * Creates a new {@link MysqlOrderDAO}.
     *
     * @param source The source acted upon.
     */
    public MysqlOrderDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the {@link Order}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Order}s.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Order> getOrders(Constraint... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Order> orders = new ArrayList<>();
            final String SQL = generator.generate("SELECT * FROM orders " +
                                                  "INNER JOIN customers ON orders.customer = customers.id " +
                                                  "INNER JOIN claddings ON orders.cladding = claddings.id " +
                                                  "INNER JOIN roofings ON orders.roofing = roofings.id " +
                                                  "INNER JOIN shed ON orders.shed = sheds.id", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
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
