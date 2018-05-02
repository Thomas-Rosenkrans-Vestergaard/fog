package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerRecord;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeRecord;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringRecord;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.MaterialRecord;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferRecord;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMysqlDAO
{

    /**
     * The {@link MysqlDataSource} to perform operations upon.
     */
    private final MysqlDataSource source;

    /**
     * The cached connection for reuse.
     */
    private Connection connection = null;

    /**
     * Creates a new {@link AbstractMysqlDAO}.
     *
     * @param source The {@link MysqlDataSource} to perform operations upon.
     */
    public AbstractMysqlDAO(MysqlDataSource source)
    {
        this.source = source;
    }

    /**
     * Returns a connection from the provided {@code MysqlDataSource} with autocommit disabled.
     *
     * @return The connection from the provided {@code MysqlDataSource} with autocommit disabled.
     * @throws SQLException When a new connection cannot be created.
     */
    protected final Connection getConnection() throws SQLException
    {
        if (connection == null) {
            connection = source.getConnection();
            connection.setAutoCommit(false);
        }

        return connection;
    }

    /**
     * Creates a new {@link Cladding} using the provided {@code ResultSet}.
     *
     * @param table     The name of the table.
     * @param claddings The {@code ResultSet} from which to create the instance of {@link Cladding}.
     * @return The newly created instance of {@link Cladding}.
     * @throws SQLException When an exception occurs while creating the entity.
     */
    protected Cladding createCladding(String table, ResultSet claddings) throws SQLException
    {
        return new CladdingRecord(
                claddings.getInt(table + ".id"),
                claddings.getString(table + ".name"),
                claddings.getString(table + ".description"),
                claddings.getBoolean(table + ".active")
        );
    }

    /**
     * Creates a new {@link Customer} instance from the provided {@code ResultSet}.
     *
     * @param table     The name of the table.
     * @param resultSet The {@code ResultSet} from which to create the {@link Customer} instance.
     * @return The newly created {@link Customer} instance.
     * @throws SQLException When an exception occurs while creating the entity.
     */
    protected Customer createCustomer(String table, ResultSet resultSet) throws SQLException
    {
        return new CustomerRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".name"),
                resultSet.getString(table + ".address"),
                resultSet.getString(table + ".email"),
                resultSet.getString(table + ".phone"),
                resultSet.getString(table + ".password"),
                resultSet.getBoolean(table + ".active"),
                resultSet.getBoolean(table + ".confirmed"),
                resultSet.getTimestamp(table + ".created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Flooring} using the provided {@code ResultSet}.
     *
     * @param table     The name of the table.
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Flooring}.
     * @return The newly created instance of {@link Flooring}.
     * @throws SQLException When an exception occurs while creating the entity.
     */
    protected Flooring createFlooring(String table, ResultSet resultSet) throws SQLException
    {
        return new FlooringRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".name"),
                resultSet.getString(table + ".description"),
                resultSet.getBoolean(table + ".active")
        );
    }

    /**
     * Creates a new {@link Roofing} implementation using the provided {@link ResultSet}.
     *
     * @param table     The name of the table.
     * @param resultSet The {@code ResultSet} from which to create the {@link Roofing} implementation.
     * @return The resulting instance of {@link Roofing}.
     * @throws SQLException When an exception occurs while creating the entity.
     */
    protected Roofing createRoofing(String table, ResultSet resultSet) throws SQLException
    {
        return new RoofingRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".name"),
                resultSet.getString(table + ".description"),
                resultSet.getInt(table + ".minimum_slope"),
                resultSet.getInt(table + ".maximum_slope"),
                resultSet.getBoolean(table + ".active")
        );
    }

    /**
     * Creates a new {@link Shed} from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Shed} implementation.
     * @return The resulting instance of {@link Shed}.
     * @throws SQLException When an exception occurs while creating the entity.
     */
    protected Shed createShed(ResultSet resultSet, String table, String claddingTable, String flooringTable) throws SQLException
    {
        return new ShedRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getInt(table + ".width"),
                resultSet.getInt(table + ".depth"),
                resultSet.getInt(table + ".cladding"),
                createCladding(claddingTable, resultSet),
                resultSet.getInt(table + ".flooring"),
                createFlooring(flooringTable, resultSet)
        );
    }

    /**
     * Creates a new {@link Employee} instance from the provided {@code ResultSet}.
     *
     * @param employees
     * @param roles     The {@code ResultSet} from which to create the {@link Employee} instance.
     * @return The newly created {@link Employee} instance.
     * @throws SQLException
     */
    protected Employee createEmployee(String table, ResultSet employees, String rolesTable, ResultSet roles) throws SQLException
    {
        return new EmployeeRecord(
                employees.getInt(table + ".id"),
                employees.getString(table + ".name"),
                employees.getString(table + ".username"),
                employees.getString(table + ".password"),
                employees.getBoolean(table + ".active"),
                createRoleSet(rolesTable, roles),
                employees.getTimestamp(table + ".created_at").toLocalDateTime()
        );
    }

    private Set<Role> createRoleSet(String table, ResultSet resultSet) throws SQLException
    {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString(table + ".role")));
        }

        return roles;
    }

    protected Material createMaterial(String table, ResultSet resultSet) throws SQLException
    {
        return new MaterialRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".number"),
                resultSet.getString(table + ".description"),
                resultSet.getInt(table + ".price"),
                resultSet.getInt(table + ".unit"),
                resultSet.getInt(table + ".width"),
                resultSet.getInt(table + ".height")
        );
    }

    /**
     * Creates a new {@link Order} instance from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Flooring}.
     * @return The newly create instance of {@link Order}.
     * @throws SQLException
     */
    protected Order createOrder(ResultSet resultSet,
                                String table,
                                String customerTable,
                                String claddingTable,
                                String roofingTable,
                                String shedTable,
                                String shedCladdingTable,
                                String shedFlooringTable) throws SQLException
    {
        return new OrderRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getInt(table + ".customer"),
                createCustomer(customerTable, resultSet),
                resultSet.getInt(table + ".cladding"),
                createCladding(claddingTable, resultSet),
                resultSet.getInt(table + ".width"),
                resultSet.getInt(table + ".length"),
                resultSet.getInt(table + ".height"),
                resultSet.getInt(table + ".roofing"),
                createRoofing(roofingTable, resultSet),
                resultSet.getInt(table + ".slope"),
                RafterChoice.from(resultSet.getInt(table + ".rafters")),
                createShed(resultSet, shedTable, shedCladdingTable, shedFlooringTable),
                resultSet.getBoolean(table + ".active"),
                resultSet.getInt(table + ".offers"),
                resultSet.getTimestamp(table + ".created_at").toLocalDateTime()
        );
    }

    protected Offer createOffer(ResultSet resultSet,
                                String table,
                                String orderTable,
                                String customerTable,
                                String orderCladdingTable,
                                String orderRoofingTable,
                                String shedTable,
                                String shedCladdingTable,
                                String shedFlooringsTable,
                                String employeeTable,
                                ResultSet roles) throws SQLException
    {
        Order    order    = createOrder(resultSet, orderTable, customerTable, orderCladdingTable, orderRoofingTable, shedTable, shedCladdingTable, shedFlooringsTable);
        Employee employee = createEmployee(employeeTable, resultSet, "roles", roles);

        return new OfferRecord(
                resultSet.getInt(table + ".id"),
                order, order.getId(),
                employee,
                employee.getId(),
                resultSet.getInt(table + ".price"),
                resultSet.getTimestamp(table + ".created_at").toLocalDateTime());
    }
}
