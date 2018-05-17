package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<OrderColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
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
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public List<Order> get(Constraints<OrderColumn> constraints) throws MysqlDataAccessException
    {
        final List<Order> orders = new ArrayList<>();
        final String SQL = generator.generate(
                "SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS `o.offers`, " +
                        "(SELECT count(offers.id) FROM offers WHERE offers.order = o.id AND offers.status = 'OPEN') as `o.open_offers`, " +
                        "CONCAT_WS('.', customers.name, customers.email, roofings.name, claddings.name, floorings.name, " +
                        "o.width, o.height, o.length, o.rafters) as `o.search` " +
                        "FROM orders o " +
                        "INNER JOIN customers ON o.customer = customers.id " +
                        "INNER JOIN roofings ON o.roofing = roofings.id " +
                        "LEFT  JOIN sheds ON o.shed = sheds.id " +
                        "LEFT  JOIN claddings ON sheds.cladding = claddings.id " +
                        "LEFT  JOIN floorings ON sheds.flooring = floorings.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                orders.add(createOrder(resultSet, "o", "customers", "roofings", "sheds", "claddings", "floorings"));

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
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public Order first(Constraints<OrderColumn> constraints) throws MysqlDataAccessException
    {
        List<Order> orders = get(constraints.limit(1));

        return orders.isEmpty() ? null : orders.get(0);
    }

    /**
     * Inserts a new order into data storage.
     *
     * @param blueprint The order blueprint that contains the information necessary to create the order.
     * @return The new order.
     * @throws DataAccessException When a data storage exception occurs during the operation.
     */
    @Override public Order create(OrderBlueprint blueprint) throws DataAccessException
    {
        String orderSQL = "INSERT INTO orders " +
                "(customer, width, `length`, height, roofing, slope, rafters, shed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String shedSQL = "INSERT INTO sheds (depth, cladding, flooring) VALUES (?, ?, ?)";

        try {
            Connection con = getConnection();
            try {

                int           shedId        = -1;
                ShedBlueprint shedBlueprint = blueprint.getShedBlueprint();
                if (shedBlueprint != null) {
                    ShedBlueprint shed = blueprint.getShedBlueprint();
                    if (shed != null) {
                        try (PreparedStatement shedStatement = con.prepareStatement(shedSQL, RETURN_GENERATED_KEYS)) {
                            shedStatement.setInt(1, shedBlueprint.getDepth());
                            shedStatement.setInt(2, shed.getCladdingId());
                            shedStatement.setInt(3, shed.getFlooringId());
                            shedStatement.executeUpdate();

                            ResultSet generated = shedStatement.getGeneratedKeys();
                            generated.first();
                            shedId = generated.getInt(1);
                        }
                    }
                }

                int orderId;
                try (PreparedStatement orderStatement = con.prepareStatement(orderSQL, RETURN_GENERATED_KEYS)) {
                    orderStatement.setInt(1, blueprint.getCustomerId());
                    orderStatement.setInt(2, blueprint.getWidth());
                    orderStatement.setInt(3, blueprint.getLength());
                    orderStatement.setInt(4, blueprint.getHeight());
                    orderStatement.setInt(5, blueprint.getRoofingId());
                    orderStatement.setInt(6, blueprint.getSlope());
                    orderStatement.setInt(7, blueprint.getRafterChoice().getId());
                    if (shedId != -1)
                        orderStatement.setInt(8, shedId);
                    else
                        orderStatement.setNull(8, Types.INTEGER);
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
     * @param updater The order updater that contains the information necessary to create the order.
     * @return {@code true} if the record was updated.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(OrderUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String orderSQL = "UPDATE orders SET width = ?, `length` = ?, height = ?, " +
                    "roofing = ?, slope = ?, rafters = ?, active = ?, shed = ? WHERE id = ?";
            Connection connection = getConnection();

            ShedUpdater shed = updater.getShedUpdater();

            try {
                try (PreparedStatement orderStatement = connection.prepareStatement(orderSQL)) {
                    orderStatement.setInt(1, updater.getWidth());
                    orderStatement.setInt(2, updater.getLength());
                    orderStatement.setInt(3, updater.getHeight());
                    orderStatement.setInt(4, updater.getRoofingId());
                    orderStatement.setInt(5, updater.getSlope());
                    orderStatement.setInt(6, updater.getRafterChoice().getId());
                    orderStatement.setBoolean(7, updater.isActive());
                    if (shed != null && shed.getId() > 0)
                        orderStatement.setInt(8, shed.getId());
                    else
                        orderStatement.setNull(8, Types.INTEGER);
                    orderStatement.setInt(9, updater.getId());
                    orderStatement.executeUpdate();
                }

                if (shed != null) {
                    final String shedSQL = "UPDATE sheds SET `depth` = ?, `cladding` = ?, `flooring` = ? WHERE id = (SELECT shed FROM orders WHERE orders.id = ?)";
                    try (PreparedStatement shedStatement = connection.prepareStatement(shedSQL)) {
                        shedStatement.setInt(1, shed.getDepth());
                        shedStatement.setInt(2, shed.getCladdingId());
                        shedStatement.setInt(3, shed.getFlooringId());
                        shedStatement.setInt(4, updater.getId());
                        int updated = shedStatement.executeUpdate();

                        if (updated == 0) {
                            String createShedSQL = "INSERT INTO sheds (depth, cladding, flooring) VALUES (?,?,?)";
                            try (PreparedStatement createShed = connection.prepareStatement(createShedSQL, RETURN_GENERATED_KEYS)) {
                                createShed.setInt(1, shed.getDepth());
                                createShed.setInt(2, shed.getCladdingId());
                                createShed.setInt(3, shed.getFlooringId());
                                createShed.executeUpdate();
                                ResultSet newShed = createShed.getGeneratedKeys();
                                newShed.first();

                                String updateSQL = "UPDATE orders SET shed = ? WHERE id = ?";
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                                    updateStatement.setInt(1, newShed.getInt(1));
                                    updateStatement.setInt(2, updater.getId());
                                    updateStatement.executeUpdate();
                                }
                            }
                        }
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
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public int getNumberOfNewOrders() throws MysqlDataAccessException
    {
        final String SQL = "SELECT count(" +
                "(SELECT orders.id FROM orders LEFT JOIN offers ON orders.id = offers.order " +
                "WHERE orders.active = b'1' && offers.id IS NULL)) AS count";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the number of orders in the data storage.
     *
     * @return The number of orders in the data storage.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public int size() throws MysqlDataAccessException
    {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT count(*) FROM orders")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
