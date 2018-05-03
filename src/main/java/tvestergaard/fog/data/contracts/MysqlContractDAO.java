package tvestergaard.fog.data.contracts;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static tvestergaard.fog.data.constraints.Constraint.*;

public class MysqlContractDAO extends AbstractMysqlDAO implements ContractDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<ContractColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<ContractColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlContractDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlContractDAO}.
     */
    public MysqlContractDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the contracts in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting contracts.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Contract> get(Constraint<ContractColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Contract> contracts = new ArrayList<>();
        final String SQL = generator.generate("SELECT *, (SELECT count(*) FROM offers WHERE `order` = o.id) AS `o.offers`, " +
                "(SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = o_emp.id) as `o_emp.roles`, " +
                "(SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = c_emp.id) as `c_emp.roles` " +
                "FROM contracts " +
                "INNER JOIN employees c_emp ON contracts.employee = c_emp.id " +
                "INNER JOIN offers ON contracts.offer = offers.id " +
                "INNER JOIN orders o ON offers.order = o.id " +
                "INNER JOIN customers ON o.customer = customers.id " +
                "INNER JOIN claddings o_cladding ON o.cladding = o_cladding.id " +
                "INNER JOIN roofings ON o.roofing = roofings.id " +
                "LEFT  JOIN sheds ON o.id = sheds.order " +
                "LEFT  JOIN claddings s_cladding ON sheds.cladding = s_cladding.id " +
                "LEFT  JOIN floorings ON sheds.flooring = floorings.id " +
                "INNER JOIN employees o_emp ON offers.employee = o_emp.id", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                contracts.add(createContract(resultSet, "contracts", "c_emp", "offers", "o", "customers", "o_cladding", "roofings", "sheds", "s_cladding", "floorings", "o_emp"));

            return contracts;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first contract matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first contract matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Contract first(Constraint<ContractColumn>... constraints) throws MysqlDataAccessException
    {
        List<Contract> contracts = get(append(constraints, limit(1)));

        return contracts.isEmpty() ? null : contracts.get(0);
    }

    /**
     * Inserts a new contract into the data storage.
     *
     * @param blueprint The contract blueprint that contains the information necessary to create the contract.
     * @return The contract instance representing the newly created contract.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Contract create(ContractBlueprint blueprint) throws MysqlDataAccessException
    {

        try {
            Connection connection = getConnection();

            String bomSQL = "INSERT INTO bom VALUES ()";
            try (PreparedStatement bomStatement = connection.prepareStatement(bomSQL, RETURN_GENERATED_KEYS)) {
                ResultSet resultSet = bomStatement.getGeneratedKeys();
                bomStatement.executeUpdate();
                int bomId = resultSet.getInt(1);

                final String contractSQL = "INSERT INTO contracts (offer, employee, bom) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(contractSQL, RETURN_GENERATED_KEYS)) {
                    statement.setInt(1, blueprint.getOfferId());
                    statement.setInt(2, blueprint.getEmployeeId());
                    statement.setInt(3, bomId);
                    statement.executeUpdate();
                    ResultSet generated = statement.getGeneratedKeys();

                    return first(where(eq(ContractColumn.ID, generated.getInt(1))));
                }
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
