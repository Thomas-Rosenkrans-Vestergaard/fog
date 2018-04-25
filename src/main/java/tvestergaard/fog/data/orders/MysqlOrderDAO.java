package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
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
     * The generator used to generate SQL for the {@link Constraint}s provided to this DAO.
     */
    private final StatementGenerator<OrderColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the {@link Constraint}s provided to this DAO.
     */
    private final StatementBinder<OrderColumn> binder = new StatementBinder();

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
     * Returns the orders in the data storage.
     * The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws DataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Order> get(Constraint<OrderColumn>... constraints) throws DataAccessException
    {
        final List<Order> orders = new ArrayList<>();
        final String SQL = generator.generate("SELECT * FROM orders " +
                                              "INNER JOIN customers ON orders.customer = customers.id " +
                                              "INNER JOIN claddings ON orders.cladding = claddings.id " +
                                              "INNER JOIN roofings ON orders.roofing = roofings.id " +
                                              "LEFT  JOIN sheds ON orders.shed = sheds.id " +
                                              "LEFT  JOIN claddings shedcladding ON sheds.cladding = claddings.id " +
                                              "LEFT  JOIN floorings ON sheds.flooring = floorings.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                orders.add(createOrder(resultSet));

            return orders;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new {@link Order} instance from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Flooring}.
     * @return The newly create instance of {@link Order}.
     * @throws SQLException
     */
    public Order createOrder(ResultSet resultSet) throws SQLException
    {
        return new OrderRecord(
                resultSet.getInt("orders.id"),
                createCustomer(resultSet),
                Order.Type.from(resultSet.getInt("orders.type")),
                createCladding(resultSet),
                resultSet.getInt("orders.width"),
                resultSet.getInt("orders.length"),
                resultSet.getInt("orders.height"),
                createRoofing(resultSet),
                resultSet.getInt("orders.slope"),
                Order.RaftersConstruction.from(resultSet.getInt("orders.rafters_type")),
                createShed(resultSet),
                resultSet.getTimestamp("orders.created_at").toLocalDateTime()
        );
    }


    /**
     * Creates a new {@link Shed} from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Shed} implementation.
     * @return The resulting instance of {@link Shed}.
     * @throws SQLException
     */
    public Shed createShed(ResultSet resultSet) throws SQLException
    {
        return new ShedRecord(
                resultSet.getInt("sheds.id"),
                resultSet.getInt("sheds.width"),
                resultSet.getInt("sheds.depth"),
                createShedCladding(resultSet),
                createFlooring(resultSet)
        );
    }

    /**
     * Creates a new {@link Cladding} using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Cladding}.
     * @return The newly created instance of {@link Cladding}.
     * @throws SQLException
     */
    public Cladding createShedCladding(ResultSet resultSet) throws SQLException
    {
        return new CladdingRecord(
                resultSet.getInt("shedcladding.id"),
                resultSet.getString("shedcladding.name"),
                resultSet.getString("shedcladding.description"),
                resultSet.getInt("shedcladding.price_per_square_meter"),
                resultSet.getBoolean("shedcladding.active")
        );
    }
}
