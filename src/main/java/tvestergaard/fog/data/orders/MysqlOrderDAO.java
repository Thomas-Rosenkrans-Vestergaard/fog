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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        final String SQL = generator.generate(
                "SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS offers FROM orders o " +
                        "INNER JOIN customers ON o.customer = customers.id " +
                        "INNER JOIN claddings o_cladding ON o.cladding = o_cladding.id " +
                        "INNER JOIN roofings ON o.roofing = roofings.id " +
                        "LEFT  JOIN sheds ON o.id = sheds.order " +
                        "LEFT  JOIN claddings s_cladding ON sheds.cladding = s_cladding.id " +
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
     * @param blueprint The order blueprint that contains the information necessary to create the order.
     * @return The new order.
     * @throws DataAccessException When an exception occurs during the operation.
     */
    @Override public Order create(OrderBlueprint blueprint) throws DataAccessException
    {
        String orderSQL = "INSERT INTO orders " +
                "(customer, cladding, width, `length`, height, roofing, slope, rafters) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String shedSQL = "INSERT INTO sheds (`order`, width, depth, cladding, flooring) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = getConnection();
            try {

                int orderId;
                try (PreparedStatement orderStatement = con.prepareStatement(orderSQL, RETURN_GENERATED_KEYS)) {
                    orderStatement.setInt(1, blueprint.getCustomer().getId());
                    orderStatement.setInt(2, blueprint.getCladding().getId());
                    orderStatement.setInt(3, blueprint.getWidth());
                    orderStatement.setInt(4, blueprint.getLength());
                    orderStatement.setInt(5, blueprint.getHeight());
                    orderStatement.setInt(6, blueprint.getRoofing().getId());
                    orderStatement.setInt(7, blueprint.getSlope());
                    orderStatement.setInt(8, blueprint.getRafterChoice().getId());
                    orderStatement.executeUpdate();
                    ResultSet resultSet = orderStatement.getGeneratedKeys();
                    resultSet.first();
                    orderId = resultSet.getInt(1);
                }

                ShedBlueprint shed = blueprint.getShed();
                if (shed != null) {
                    try (PreparedStatement shedStatement = con.prepareStatement(shedSQL)) {
                        shedStatement.setInt(1, orderId);
                        shedStatement.setInt(2, shed.getWidth());
                        shedStatement.setInt(3, shed.getDepth());
                        shedStatement.setInt(4, shed.getCladding().getId());
                        shedStatement.setInt(5, shed.getFlooring().getId());
                        shedStatement.executeUpdate();
                    }
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
     * @param updater The order updater that contains the information necessary to create the order.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public boolean update(OrderUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String orderSQL = "UPDATE orders SET cladding = ?, width = ?, `length` = ?, height = ?, " +
                    "roofing = ?, slope = ?, rafters = ?, active = ? WHERE id = ?";
            final String shedSQL    = "UPDATE sheds SET `order` = ?, `width` = ?, `depth` = ?, `cladding` = ?, `flooring` = ? WHERE id = ?";
            Connection   connection = getConnection();
            try {
                try (PreparedStatement orderStatement = connection.prepareStatement(orderSQL)) {
                    orderStatement.setInt(1, updater.getCladding().getId());
                    orderStatement.setInt(2, updater.getWidth());
                    orderStatement.setInt(3, updater.getLength());
                    orderStatement.setInt(4, updater.getHeight());
                    orderStatement.setInt(5, updater.getRoofing().getId());
                    orderStatement.setInt(6, updater.getSlope());
                    orderStatement.setInt(7, updater.getRafterChoice().getId());
                    orderStatement.setBoolean(8, updater.isActive());
                    orderStatement.setInt(9, updater.getId());
                    orderStatement.executeUpdate();
                }

                ShedUpdater shed = updater.getShed();
                if (shed == null) {
                    try (PreparedStatement delete = connection.prepareStatement("DELETE FROM sheds WHERE `order` = ?")) {
                        delete.setInt(1, updater.getId());
                        delete.executeUpdate();
                    }
                } else {
                    try (PreparedStatement update = connection.prepareStatement(shedSQL)) {
                        update.setInt(1, updater.getId());
                        update.setInt(2, shed.getWidth());
                        update.setInt(3, shed.getDepth());
                        update.setInt(4, shed.getCladding().getId());
                        update.setInt(5, shed.getFlooring().getId());
                        update.executeUpdate();
                    }
                }

                connection.commit();

                return true;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the number of orders that are both active, and have not yet received any offers.
     *
     * @return The number of such orders.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public int getNumberOfNewOrders() throws MysqlDataAccessException
    {
        final String SQL = "SELECT COUNT(*) AS count FROM " +
                "(SELECT (" +
                "SELECT count(*) FROM offers WHERE `order` = offers.id) AS offers FROM orders WHERE orders.active = b'1'" +
                " HAVING offers = 0) AS `al`";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getInt("count");
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
                resultSet.getBoolean("o.active"),
                resultSet.getInt("offers"),
                resultSet.getTimestamp("o.created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Cladding} using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Cladding}.
     * @return The newly created instance of {@link Cladding}.
     * @throws SQLException
     */
    protected Cladding createCladding(ResultSet resultSet) throws SQLException
    {
        return new CladdingRecord(
                resultSet.getInt("o_cladding.id"),
                resultSet.getString("o_cladding.name"),
                resultSet.getString("o_cladding.description"),
                resultSet.getInt("o_cladding.price_per_square_meter"),
                resultSet.getBoolean("o_cladding.active")
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
                resultSet.getInt("s_cladding.id"),
                resultSet.getString("s_cladding.name"),
                resultSet.getString("s_cladding.description"),
                resultSet.getInt("s_cladding.price_per_square_meter"),
                resultSet.getBoolean("s_cladding.active")
        );
    }
}
