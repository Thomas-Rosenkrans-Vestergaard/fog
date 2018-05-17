package tvestergaard.fog.data.offers;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.purchases.PurchaseBlueprint;
import tvestergaard.fog.data.purchases.PurchaseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.offers.OfferColumn.ORDER;

public class MysqlOfferDAO extends AbstractMysqlDAO implements OfferDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<OfferColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<OfferColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlOfferDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlOfferDAO}.
     */
    public MysqlOfferDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the offers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting offers.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Offer> get(Constraints<OfferColumn> constraints) throws MysqlDataAccessException
    {
        final List<Offer> offers = new ArrayList<>();
        final String SQL = generator.generate(
                "SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS `o.offers`, " +
                        "(SELECT count(offers.id) FROM offers WHERE offers.order = o.id AND offers.status = 'OPEN') as `o.open_offers`, " +
                        "(SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = employees.id) as `employees.roles`, " +
                        "CONCAT_WS('.', customers.name, roofings.name, employees.name, employees.username, claddings.name, offers.status) as `offers.search` " +
                        "FROM offers " +
                        "INNER JOIN orders o ON offers.order = o.id " +
                        "INNER JOIN customers ON o.customer = customers.id " +
                        "INNER JOIN roofings ON o.roofing = roofings.id " +
                        "LEFT  JOIN sheds ON sheds.id = o.shed " +
                        "LEFT  JOIN claddings ON sheds.cladding = claddings.id " +
                        "LEFT  JOIN floorings ON sheds.flooring = floorings.id " +
                        "INNER JOIN employees ON offers.employee = employees.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                offers.add(createOffer(resultSet, "offers", "o", "customers", "roofings", "sheds", "claddings", "floorings", "employees"));

            return offers;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the offers issued to the order with the provided id.
     *
     * @param order The id of the order to return the related offers of.
     * @return The offers related to the order with the provided id.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Offer> get(int order) throws MysqlDataAccessException
    {
        return get(where(eq(ORDER, order)));
    }

    /**
     * Returns the first offer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first offer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Offer first(Constraints<OfferColumn> constraints) throws MysqlDataAccessException
    {
        List<Offer> offers = get(constraints.limit(1));

        return offers.isEmpty() ? null : offers.get(0);
    }

    /**
     * Inserts a new offer into the data storage.
     *
     * @param blueprint The offer blueprint that contains the information necessary to create the offer.
     * @return The offer instance representing the newly created offer.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Offer create(OfferBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "INSERT INTO offers (`order`, `employee`, price) VALUES (?, ?, ?)";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, blueprint.getOrderId());
                statement.setInt(2, blueprint.getEmployeeId());
                statement.setInt(3, blueprint.getPrice());
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return first(where(eq(OfferColumn.ID, generated.getInt(1))));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Rejects the offer with the provided id.
     * <p>
     * To accept an offer, use the {@link PurchaseDAO#create(PurchaseBlueprint)} method,
     * that will mark the provided offer accepted.
     *
     * @param offerId The id of the offer to mark reject.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public void reject(int offerId) throws MysqlDataAccessException
    {
        String SQL = "UPDATE offers SET status = ? WHERE id = ?";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, OfferStatus.REJECTED.name());
                statement.setInt(2, offerId);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the number of offers in the data storage.
     *
     * @return The number of offers in the data storage.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public int size() throws MysqlDataAccessException
    {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT count(*) FROM offers")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the number of open offers for the provided customer.
     *
     * @param customer The customer to return the number of open offers for.
     * @return The number of open offers for the provided customer.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public int getNumberOfOpenOffers(int customer) throws MysqlDataAccessException
    {
        String SQL = "SELECT count(*) FROM offers " +
                "INNER JOIN orders ON offers.order = orders.id AND orders.customer = ? " +
                "WHERE offers.status = 'OPEN'";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, customer);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
