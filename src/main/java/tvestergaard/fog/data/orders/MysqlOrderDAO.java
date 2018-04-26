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
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedRecord;
import tvestergaard.fog.data.sheds.ShedSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
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
     * Returns the orders in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting orders.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public List<Order> get(Constraint<OrderColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Order> orders = new ArrayList<>();
        final String SQL = generator.generate("SELECT * FROM orders o " +
                "INNER JOIN customers ON o.customer = customers.id " +
                "INNER JOIN claddings ON o.cladding = claddings.id " +
                "INNER JOIN roofings ON o.roofing = roofings.id " +
                "LEFT  JOIN sheds ON o.shed = sheds.id " +
                "LEFT  JOIN claddings shed_cladding ON sheds.cladding = claddings.id " +
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
     * Returns the first order matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first order matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public Order first(Constraint<OrderColumn>... constraints) throws MysqlDataAccessException
    {
        List<Order> orders = get(Constraint.append(constraints, limit(1)));

        return orders.isEmpty() ? null : orders.get(0);
    }

    /**
     * Inserts a new order into data storage.
     *
     * @param customer The id of the customer who placed the order.
     * @param cladding The id of the cladding used on the order.
     * @param width    The width of the order.
     * @param length   The length of the order.
     * @param height   The height of the order.
     * @param roofing  The id of the roofing used on the order.
     * @param slope    The slope of the roofing used on the order.
     * @param rafters  The rafters construction delivered with the order.
     * @param shed     The shed to add to the order.
     * @return The new order.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    @Override
    public Order create(int customer,
                        int cladding,
                        int width,
                        int length,
                        int height,
                        int roofing,
                        int slope,
                        RafterChoice rafters,
                        ShedSpecification shed) throws DataAccessException
    {
        String orderSQL = "INSERT INTO orders " +
                "(customer, cladding, width, `length`, height, roofing, slope, rafters, shed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String shedSQL = "INSERT INTO sheds (width, depth, cladding, flooring) VALUES (?, ?, ?, ?)";

        try {
            Connection con = getConnection();
            try {

                int shedId = -1;
                if (shed != null) {
                    try (PreparedStatement shedStatement = con.prepareStatement(shedSQL, RETURN_GENERATED_KEYS)) {
                        shedStatement.setInt(1, shed.getWidth());
                        shedStatement.setInt(2, shed.getDepth());
                        shedStatement.setInt(3, shed.getCladding());
                        shedStatement.setInt(4, shed.getFlooring());
                        shedStatement.executeUpdate();
                        ResultSet resultSet = shedStatement.getGeneratedKeys();
                        resultSet.first();
                        shedId = resultSet.getInt(1);
                    }
                }

                int orderId;
                try (PreparedStatement orderStatement = con.prepareStatement(orderSQL, RETURN_GENERATED_KEYS)) {
                    orderStatement.setInt(1, customer);
                    orderStatement.setInt(2, cladding);
                    orderStatement.setInt(3, width);
                    orderStatement.setInt(4, length);
                    orderStatement.setInt(5, height);
                    orderStatement.setInt(6, roofing);
                    orderStatement.setInt(7, slope);
                    orderStatement.setInt(8, rafters.getId());
                    if (shedId == -1)
                        orderStatement.setNull(9, Types.INTEGER);
                    else
                        orderStatement.setInt(9, shedId);
                    orderStatement.executeUpdate();
                    ResultSet resultSet = orderStatement.getGeneratedKeys();
                    resultSet.first();
                    orderId = resultSet.getInt(1);
                }

                con.commit();

                return first(where(eq(OrderColumn.ID, orderId)));
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code order}.
     *
     * @param order The order to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override
    public boolean update(Order order) throws MysqlDataAccessException
    {
        try {
            final String SQL = "UPDATE orders SET cladding = ?, width = ?, `length` = ?, height = ?, " +
                    "roofing = ?, slope = ?, rafters = ?, shed = ? WHERE id = ?";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, order.getCladding().getId());
                statement.setInt(2, order.getWidth());
                statement.setInt(3, order.getLength());
                statement.setInt(4, order.getHeight());
                statement.setInt(5, order.getRoofing().getId());
                statement.setInt(6, order.getSlope());
                statement.setInt(7, order.getRafterChoice().getId());
                statement.setInt(8, order.getShed() == null ? null : order.getShed().getId());
                statement.setInt(9, order.getId());
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
                resultSet.getInt("o.id"),
                createCustomer(resultSet),
                createCladding(resultSet),
                resultSet.getInt("o.width"),
                resultSet.getInt("o.length"),
                resultSet.getInt("o.height"),
                createRoofing(resultSet),
                resultSet.getInt("o.slope"),
                RafterChoice.from(resultSet.getInt("o.rafters")),
                createShed(resultSet),
                resultSet.getTimestamp("o.created_at").toLocalDateTime()
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
                resultSet.getInt("shed_cladding.id"),
                resultSet.getString("shed_cladding.name"),
                resultSet.getString("shed_cladding.description"),
                resultSet.getInt("shed_cladding.price_per_square_meter"),
                resultSet.getBoolean("shed_cladding.active")
        );
    }
}
