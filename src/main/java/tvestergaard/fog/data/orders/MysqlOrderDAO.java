package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.*;

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
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Order> get(Constraint<OrderColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Order> orders = new ArrayList<>();
        final String SQL = generator.generate("SELECT * FROM (SELECT * FROM orders " +
                                              "INNER JOIN customers ON orders.customer = customers.id " +
                                              "INNER JOIN claddings ON orders.cladding = claddings.id " +
                                              "INNER JOIN roofings ON orders.roofing = roofings.id " +
                                              "LEFT  JOIN sheds ON orders.shed = sheds.id " +
                                              "LEFT  JOIN claddings shedcladding ON sheds.cladding = claddings.id " +
                                              "LEFT  JOIN floorings ON sheds.flooring = floorings.id)", constraints);
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
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no
     * constraints matches the provided constraints.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public Order first(Constraint<OrderColumn>... constraints) throws MysqlDataAccessException
    {
        List<Order> orders = get(Constraint.append(constraints, limit(1)));

        return orders.isEmpty() ? null : orders.get(0);
    }

    /**
     * Inserts a new order into data storage.
     *
     * @param customer The customer who placed the order.
     * @param type     The type of order to place.
     * @param cladding The cladding used on the order.
     * @param width    The width of the order.
     * @param length   The length of the order.
     * @param height   The height of the order.
     * @param roofing  The roofing used on the order.
     * @param slope    The slope of the roofing used on the order.
     * @param rafters  The rafters construction devilered with the order.
     * @param shed     The shed to add to the order.
     * @return The new order.
     * @throws MysqlDataAccessException When an exception occurs during the operation.
     */
    @Override
    public Order create(Customer customer,
            Order.Type type,
            Cladding cladding,
            int width,
            int length,
            int height,
            Roofing roofing,
            int slope,
            Order.Rafters rafters,
            Shed shed) throws MysqlDataAccessException
    {

        String shedSQL = "INSERT INTO sheds " +
                         "(width, depth, cladding, flooring) " +
                         "VALUES (?, ?, ?, ?)";

        String orderSQL = "INSERT INTO orders " +
                          "(customer, `type`, cladding, width, `length`, height, roofing, slope, rafters_type, shed) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = getConnection();
            try {

                int shedId = 0;
                if (shed != null) {
                    try (PreparedStatement shedStatement = connection.prepareStatement(shedSQL, Statement.RETURN_GENERATED_KEYS)) {
                        shedStatement.setInt(1, shed.getWidth());
                        shedStatement.setInt(2, shed.getDepth());
                        shedStatement.setInt(3, shed.getCladding().getId());
                        shedStatement.setInt(4, shed.getFlooring().getId());
                        shedStatement.executeUpdate();
                        ResultSet resultSet = shedStatement.getGeneratedKeys();
                        resultSet.first();
                        shedId = resultSet.getInt(1);
                    }
                }

                int orderId = 0;
                try (PreparedStatement orderStatement = connection.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    orderStatement.setInt(1, customer.getId());
                    orderStatement.setInt(2, type.getId());
                    orderStatement.setInt(3, cladding.getId());
                    orderStatement.setInt(4, width);
                    orderStatement.setInt(5, length);
                    orderStatement.setInt(6, height);
                    orderStatement.setInt(7, roofing.getId());
                    orderStatement.setInt(8, slope);
                    orderStatement.setInt(9, rafters.getId());
                    orderStatement.setInt(10, shedId);
                    orderStatement.executeUpdate();
                    ResultSet resultSet = orderStatement.getGeneratedKeys();
                    resultSet.first();
                    orderId = resultSet.getInt(1);
                }

                connection.commit();
                return first(where(eq(OrderColumn.ID, orderId)));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

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
    protected Order createOrder(ResultSet resultSet) throws SQLException
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
                Order.Rafters.from(resultSet.getInt("orders.rafters_type")),
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
    protected Shed createShed(ResultSet resultSet) throws SQLException
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
    protected Cladding createShedCladding(ResultSet resultSet) throws SQLException
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
