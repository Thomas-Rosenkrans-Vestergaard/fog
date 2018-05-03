package tvestergaard.fog.data.employees;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.employees.EmployeeColumn.ID;

public class MysqlEmployeeDAO extends AbstractMysqlDAO implements EmployeeDAO
{

    /**
     * The generator used to generate SQL for the {@link Constraint}s provided to this DAO.
     */
    private final StatementGenerator<EmployeeColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the {@link Constraint}s provided to this DAO.
     */
    private final StatementBinder<EmployeeColumn> binder = new StatementBinder();

    /**
     * Creates a new {@link MysqlEmployeeDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link EmployeeDAO}.
     */
    public MysqlEmployeeDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the employees in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting employees.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<Employee> get(Constraint<EmployeeColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Employee> employees = new ArrayList<>();
        final String SQL = generator.generate(
                "SELECT * , (SELECT GROUP_CONCAT(roles.role SEPARATOR ',') FROM roles WHERE employee = employees.id) " +
                        "as `employees.roles` FROM employees", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                employees.add(createEmployee("employees", resultSet));

            return employees;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first employee matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first employee matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Employee first(Constraint<EmployeeColumn>... constraints) throws MysqlDataAccessException
    {
        List<Employee> employees = get(append(constraints, limit(1)));

        return employees.isEmpty() ? null : employees.get(0);
    }

    /**
     * Inserts a new employee into the data storage.
     *
     * @param blueprint The cladding blueprint that contains the information necessary to create the cladding.
     * @return The employee instance representing the newly created employee.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Employee create(EmployeeBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "INSERT INTO employees (`name`, username, `password`, active) VALUES (?, ?, ?, ?)";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getName());
                statement.setString(2, blueprint.getUsername());
                statement.setString(3, blueprint.getPassword());
                statement.setBoolean(4, blueprint.isActive());
                statement.executeUpdate();
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                int employeeId = generated.getInt(1);

                String roleSQL = "INSERT INTO roles (`employee`, `role`) VALUES (?, ?)";
                try (PreparedStatement roleStatement = connection.prepareStatement(roleSQL)) {
                    roleStatement.setInt(1, employeeId);
                    for (Role role : blueprint.getRoles()) {
                        roleStatement.setString(2, role.name());
                        roleStatement.executeUpdate();
                    }
                }

                connection.commit();

                return first(where(eq(ID, generated.getInt(1))));
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code employee}.
     *
     * @param updater The cladding updater that contains the information necessary to create the cladding.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(EmployeeUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE employees SET `name` = ?, username = ?, `password` = ?, active = ?  WHERE id = ?";
            Connection   connection = getConnection();
            try {
                try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                    statement.setString(1, updater.getName());
                    statement.setString(2, updater.getUsername());
                    statement.setString(3, updater.getPassword());
                    statement.setBoolean(4, updater.isActive());
                    statement.setInt(5, updater.getId());
                    statement.executeUpdate();
                }
                String deleteSQL = "DELETE FROM roles WHERE employee = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
                    statement.setInt(1, updater.getId());
                    statement.executeUpdate();
                }

                String rolesSQL = "INSERT INTO roles (employee, `role`) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(rolesSQL)) {
                    statement.setInt(1, updater.getId());
                    for (Role role : updater.getRoles()) {
                        statement.setString(2, role.name());
                        statement.executeUpdate();
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
}
