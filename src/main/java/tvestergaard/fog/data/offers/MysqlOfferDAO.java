package tvestergaard.fog.data.offers;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @Override public List<Offer> get(Constraint<OfferColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Offer> offers = new ArrayList<>();
        final String SQL = generator.generate(
                "SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS `o.offers` FROM offers " +
                        "INNER JOIN orders o ON offers.order = o.id " +
                        "INNER JOIN customers ON o.customer = customers.id " +
                        "INNER JOIN claddings o_cladding ON o.cladding = o_cladding.id " +
                        "INNER JOIN roofings ON o.roofing = roofings.id " +
                        "LEFT  JOIN sheds ON o.id = sheds.order " +
                        "LEFT  JOIN claddings s_cladding ON sheds.cladding = s_cladding.id " +
                        "LEFT  JOIN floorings ON sheds.flooring = floorings.id " +
                        "INNER JOIN employees ON offers.employee = employees.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL);
             PreparedStatement rolesStatement = getConnection().prepareStatement("SELECT * FROM roles WHERE employee = ?")) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rolesStatement.setInt(1, resultSet.getInt("employees.id"));
                ResultSet roles = rolesStatement.executeQuery();
                offers.add(createOffer(resultSet, "offers", "o", "customers", "o_cladding", "roofings", "sheds", "s_cladding", "floorings", "employees", roles));
            }

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
    @Override public Offer first(Constraint<OfferColumn>... constraints) throws MysqlDataAccessException
    {
        return null;
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
        return null;
    }
}
