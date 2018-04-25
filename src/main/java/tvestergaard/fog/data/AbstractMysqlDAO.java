package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerRecord;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringRecord;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderRecord;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingRecord;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMysqlDAO
{

    /**
     * The {@link MysqlDataSource} to perform operations upon.
     */
    private final MysqlDataSource source;

    /**
     * The cahced connection for reuse.
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
     * @throws SQLException
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
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Cladding}.
     * @return The newly created instance of {@link Cladding}.
     * @throws SQLException
     */
    public Cladding createCladding(ResultSet resultSet) throws SQLException
    {
        return new CladdingRecord(
                resultSet.getInt("claddings.id"),
                resultSet.getString("claddings.name"),
                resultSet.getString("claddings.description"),
                resultSet.getInt("claddings.price_per_square_meter"),
                resultSet.getBoolean("claddings.active")
        );
    }

    /**
     * Creates a new {@link Customer} instance from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Customer} instance.
     * @return The newly created {@link Customer} instance.
     * @throws SQLException
     */
    public Customer createCustomer(ResultSet resultSet) throws SQLException
    {
        return new CustomerRecord(
                resultSet.getInt("customers.id"),
                resultSet.getString("customers.name"),
                resultSet.getString("customers.address"),
                resultSet.getString("customers.email"),
                resultSet.getString("customers.phone"),
                resultSet.getString("customers.password"),
                Customer.ContactMethod.from(resultSet.getInt("customers.contact_method")),
                resultSet.getBoolean("customers.active"),
                resultSet.getTimestamp("customers.created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Flooring} using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Flooring}.
     * @return The newly created instance of {@link Flooring}.
     * @throws SQLException
     */
    public Flooring createFlooring(ResultSet resultSet) throws SQLException
    {
        return new FlooringRecord(
                resultSet.getInt("floorings.id"),
                resultSet.getString("floorings.name"),
                resultSet.getString("floorings.description"),
                resultSet.getInt("floorings.price_per_square_meter"),
                resultSet.getBoolean("floorings.active")
        );
    }

    /**
     * Creates a new {@link Order} instance from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the instance of {@link Flooring}.
     * @return The newly create instance of {@link Order}.
     * @throws SQLException
     */
    public Order createOrder(ResultSet resultSet) throws SQLException
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
                Order.RaftersConstruction.from(resultSet.getInt("orders.rafters_type")),
                createShed(resultSet),
                resultSet.getTimestamp("orders.created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Roofing} implementation using the provided {@link ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Roofing} implementation.
     * @return The resulting instance of {@link Roofing}.
     * @throws SQLException
     */
    public Roofing createRoofing(ResultSet resultSet) throws SQLException
    {
        return new RoofingRecord(
                resultSet.getInt("roofings.id"),
                resultSet.getString("roofings.name"),
                resultSet.getString("roofings.description"),
                resultSet.getInt("roofings.minimum_slope"),
                resultSet.getInt("roofings.maximum_slope"),
                resultSet.getInt("roofings.price_per_square_meter"),
                resultSet.getBoolean("roofings.active")
        );
    }

    /**
     * Creates a new {@link Shed} from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Shed} implementation.
     * @return The resulting instance of {@link Shed}.
     * @throws SQLException
     */
    public Shed createShed(ResultSet resultSet) throws SQLException
    {
        return new ShedRecord(
                resultSet.getInt("sheds.id"),
                resultSet.getInt("sheds.width"),
                resultSet.getInt("sheds.depth"),
                createCladding(resultSet),
                createFlooring(resultSet)
        );
    }
}
