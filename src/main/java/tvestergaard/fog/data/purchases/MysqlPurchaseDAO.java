package tvestergaard.fog.data.purchases;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.purchases.bom.BomBlueprint;
import tvestergaard.fog.data.purchases.bom.BomDrawingBlueprint;
import tvestergaard.fog.data.purchases.bom.BomLineBlueprint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;

public class MysqlPurchaseDAO extends AbstractMysqlDAO implements PurchaseDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<PurchaseColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<PurchaseColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlPurchaseDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlPurchaseDAO}.
     */
    public MysqlPurchaseDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the purchases in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting purchases.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Purchase> get(Constraints<PurchaseColumn> constraints) throws MysqlDataAccessException
    {
        final List<Purchase> purchases = new ArrayList<>();
        final String SQL = generator.generate("SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS `o.offers`, " +
                "(SELECT count(offers.id) FROM offers WHERE offers.order = o.id AND offers.status = 'OPEN') as `o.open_offers`, " +
                "(SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = o_emp.id) as `o_emp.roles`, " +
                "(SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = p_emp.id) as `p_emp.roles`, " +
                "CONCAT_WS('.', p_emp.name, p_emp.username, customers.name, customers.email, roofings.name, claddings.name, floorings.name, o_emp.name, o_emp.username, offers.price) as `purchases.search` " +
                "FROM purchases " +
                "INNER JOIN bom ON purchases.bom = bom.id " +
                "INNER JOIN employees p_emp ON purchases.employee = p_emp.id " +
                "INNER JOIN offers ON purchases.offer = offers.id " +
                "INNER JOIN orders o ON offers.order = o.id " +
                "INNER JOIN customers ON o.customer = customers.id " +
                "INNER JOIN roofings ON o.roofing = roofings.id " +
                "LEFT  JOIN sheds ON o.shed = sheds.id " +
                "LEFT  JOIN claddings ON sheds.cladding = claddings.id " +
                "LEFT  JOIN floorings ON sheds.flooring = floorings.id " +
                "INNER JOIN employees o_emp ON offers.employee = o_emp.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                purchases.add(createPurchase(resultSet, "purchases", "p_emp", "offers", "o", "customers", "roofings",
                        "sheds", "claddings", "floorings", "o_emp"));
            return purchases;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first purchase matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first purchase matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Purchase first(Constraints<PurchaseColumn> constraints) throws MysqlDataAccessException
    {
        List<Purchase> purchases = get(constraints.limit(1));

        return purchases.isEmpty() ? null : purchases.get(0);
    }

    /**
     * Inserts a new purchase into the data storage.
     *
     * @param blueprint The purchase blueprint that contains the information necessary to create the purchase.
     * @param bom       The bill of materials to include with the purchase.
     * @return The purchase instance representing the newly created purchase.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Purchase create(PurchaseBlueprint blueprint, BomBlueprint bom) throws MysqlDataAccessException
    {

        try {
            Connection connection = getConnection();

            String createSQL = "INSERT INTO bom () VALUES ()";
            try (PreparedStatement createStatement = connection.prepareStatement(createSQL, Statement.RETURN_GENERATED_KEYS)) {
                createStatement.executeUpdate();
                ResultSet bomGenerated = createStatement.getGeneratedKeys();
                bomGenerated.first();
                int bomId = bomGenerated.getInt(1);

                String createLineSQL = "INSERT INTO bom_lines (bom, material, amount, notes) VALUES (?,?,?,?)";
                try (PreparedStatement createLineStatement = connection.prepareStatement(createLineSQL)) {
                    createLineStatement.setInt(1, bomId);
                    for (BomLineBlueprint line : bom.getBlueprintLines()) {
                        createLineStatement.setInt(2, line.getMaterialId());
                        createLineStatement.setInt(3, line.getAmount());
                        createLineStatement.setString(4, line.getNotes());
                        createLineStatement.executeUpdate();
                    }
                }

                String drawingSQL = "INSERT INTO bom_drawings (bom, title, content) VALUES (?, ?, ?)";
                try (PreparedStatement drawingStatement = connection.prepareStatement(drawingSQL)) {
                    drawingStatement.setInt(1, bomId);
                    for (BomDrawingBlueprint drawingBlueprint : bom.getBlueprintDrawings()) {
                        drawingStatement.setString(2, drawingBlueprint.getTitle());
                        drawingStatement.setString(3, drawingBlueprint.getContent());
                        drawingStatement.executeUpdate();
                    }
                }

                String orderSQL = "UPDATE orders SET active = b'0' WHERE orders.id = " +
                        "(SELECT offers.order FROM offers WHERE offers.id = ?)";
                try (PreparedStatement orderStatement = connection.prepareStatement(orderSQL)) {
                    orderStatement.setInt(1, blueprint.getOfferId());
                    orderStatement.executeUpdate();
                }

                String closeSQL = "UPDATE offers SET status = 'REJECTED' WHERE `order` = " +
                        "(SELECT `order` FROM (SELECT * FROM offers) o WHERE o.id = ?)";
                try (PreparedStatement closeStatement = connection.prepareStatement(closeSQL)) {
                    closeStatement.setInt(1, blueprint.getOfferId());
                    closeStatement.executeUpdate();
                }

                String acceptSQL = "UPDATE offers SET status = 'ACCEPTED' WHERE id = ?";
                try (PreparedStatement acceptStatement = connection.prepareStatement(acceptSQL)) {
                    acceptStatement.setInt(1, blueprint.getOfferId());
                    acceptStatement.executeUpdate();
                }

                final String purchaseSQL = "INSERT INTO purchases (offer, employee, bom) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(purchaseSQL, RETURN_GENERATED_KEYS)) {
                    statement.setInt(1, blueprint.getOfferId());
                    statement.setInt(2, blueprint.getEmployeeId());
                    statement.setInt(3, bomGenerated.getInt(1));
                    statement.executeUpdate();
                    ResultSet generated = statement.getGeneratedKeys();
                    generated.first();

                    connection.commit();

                    return first(where(eq(PurchaseColumn.ID, generated.getInt(1))));
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
