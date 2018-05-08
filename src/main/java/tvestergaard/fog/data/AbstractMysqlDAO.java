package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.bom.Bom;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerRecord;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeRecord;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringRecord;
import tvestergaard.fog.data.materials.*;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferRecord;
import tvestergaard.fog.data.offers.OfferStatus;
import tvestergaard.fog.data.orders.*;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseRecord;
import tvestergaard.fog.data.roofing.*;
import tvestergaard.fog.data.models.Model;
import tvestergaard.fog.data.models.ModelRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.data.materials.DataType.STRING;

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
                resultSet.getBoolean(table + ".active"),
                RoofingType.valueOf(resultSet.getString(table + ".type"))
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
     * @return The newly created {@link Employee} instance.
     * @throws SQLException
     */
    protected Employee createEmployee(String table, ResultSet employees) throws SQLException
    {
        return new EmployeeRecord(
                employees.getInt(table + ".id"),
                employees.getString(table + ".name"),
                employees.getString(table + ".username"),
                employees.getString(table + ".password"),
                employees.getBoolean(table + ".active"),
                createRoleSet(employees, table + ".roles"),
                employees.getTimestamp(table + ".created_at").toLocalDateTime()
        );
    }

    private Set<Role> createRoleSet(ResultSet resultSet, String column) throws SQLException
    {
        String roles = resultSet.getString(column);
        if (roles == null)
            return new HashSet<>();

        String[]  strings = roles.split(",");
        Set<Role> result  = new HashSet<>();
        for (String string : strings)
            result.add(Role.valueOf(string));

        return result;
    }

    protected SimpleMaterial createSimpleMaterial(String table, String categoryTable, ResultSet resultSet) throws SQLException
    {
        return new MaterialRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".number"),
                resultSet.getString(table + ".description"),
                resultSet.getInt(table + ".price"),
                resultSet.getInt(table + ".unit"),
                resultSet.getInt(categoryTable + ".id"),
                createCategory(categoryTable, resultSet),
                new HashSet<>()
        );
    }

    protected Material createMaterial(String table, String categoryTable, ResultSet resultSet, String attributesDefinition, String attributeValues, ResultSet attributes) throws SQLException
    {
        return new MaterialRecord(
                resultSet.getInt(table + ".id"),
                resultSet.getString(table + ".number"),
                resultSet.getString(table + ".description"),
                resultSet.getInt(table + ".price"),
                resultSet.getInt(table + ".unit"),
                resultSet.getInt(categoryTable + ".id"),
                createCategory(categoryTable, resultSet),
                createAttributeSet(attributesDefinition, attributeValues, attributes)
        );
    }

    protected Category createCategory(String categoryTable, ResultSet resultSet) throws SQLException
    {
        return new CategoryRecord(
                resultSet.getInt(categoryTable + ".id"),
                resultSet.getString(categoryTable + ".name")
        );
    }

    protected Set<AttributeValue> createAttributeSet(String attributesDefinition, String attributeValues, ResultSet attributes) throws SQLException
    {
        Set<AttributeValue> result = new HashSet<>();
        while (attributes.next()) {
            result.add(createAttributeValue(attributesDefinition, attributeValues, attributes));
        }

        return result;
    }

    protected AttributeDefinition createAttributeDefinition(String definitionTable, ResultSet resultSet) throws SQLException
    {
        return new DefaultAttributeDefinition(
                resultSet.getInt(definitionTable + ".id"),
                resultSet.getString(definitionTable + ".name"),
                DataType.valueOf(resultSet.getString(definitionTable + ".data_type"))
        );
    }

    protected AttributeValue createAttributeValue(String definitionTable, String valueTable, ResultSet attributes) throws SQLException
    {
        AttributeDefinition definition = createAttributeDefinition(definitionTable, attributes);

        return new DefaultAttributeValue(
                definition,
                getAttributeValue(valueTable, attributes, definition.getDataType())
        );
    }

    private Object getAttributeValue(String valueTable, ResultSet attributes, DataType dataType) throws SQLException
    {
        if (dataType == DataType.INT)
            return attributes.getInt(valueTable + ".value");
        if (dataType == STRING)
            return attributes.getString(valueTable + ".value");

        throw new IllegalStateException("Unknown data type " + dataType.name());
    }

    protected void setAttributeValue(PreparedStatement attributeStatement, int parameter, AttributeValue attribute) throws SQLException
    {
        DataType dataType = attribute.getDefinition().getDataType();

        if (dataType == DataType.INT) {
            attributeStatement.setInt(parameter, attribute.getInt());
            return;
        }

        if (dataType == DataType.STRING) {
            attributeStatement.setString(parameter, attribute.getString());
            return;
        }

        throw new IllegalStateException("Unknown data type " + dataType.name());
    }

    protected ComponentDefinition createComponentDefinition(
            String componentDefinitionTable, ResultSet components, String categoriesTable) throws SQLException
    {
        return new ComponentDefinitionRecord(
                components.getInt(componentDefinitionTable + ".id"),
                components.getString(componentDefinitionTable + ".identifier"),
                components.getString(componentDefinitionTable + ".notes"),
                createCategory(categoriesTable, components)
        );
    }

    protected Component createComponent(String componentDefinitionTable,
                                        String materialsTable,
                                        String categoriesTable,
                                        ResultSet resultSet,
                                        String attributeDefinitions,
                                        String attributeValues,
                                        ResultSet attributes) throws SQLException
    {
        ComponentDefinition definition = createComponentDefinition(componentDefinitionTable, resultSet, categoriesTable);
        Material            material   = createMaterial(materialsTable, categoriesTable, resultSet, attributeDefinitions, attributeValues, attributes);

        return new ComponentRecord(
                definition.getId(),
                definition,
                material.getId(),
                material
        );
    }

    protected String createIn(int size)
    {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < size; x++) {
            if (x != 0) builder.append(',');
            builder.append('?');
        }

        return builder.toString();
    }

    protected Model createModel(ResultSet resultSet, String tableName) throws SQLException
    {
        return new ModelRecord(
                resultSet.getInt(tableName + ".id"),
                resultSet.getString(tableName + ".name")
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
                                String employeeTable) throws SQLException
    {
        Order    order    = createOrder(resultSet, orderTable, customerTable, orderCladdingTable, orderRoofingTable, shedTable, shedCladdingTable, shedFlooringsTable);
        Employee employee = createEmployee(employeeTable, resultSet);

        return new OfferRecord(
                resultSet.getInt(table + ".id"),
                order,
                order.getId(),
                employee,
                employee.getId(),
                resultSet.getInt(table + ".price"),
                OfferStatus.valueOf(resultSet.getString(table + ".status")),
                resultSet.getTimestamp(table + ".created_at").toLocalDateTime());
    }

    protected Purchase createPurchase(ResultSet resultSet,
                                      String table,
                                      String purchaseEmployeeTable,
                                      String offerTable,
                                      String orderTable,
                                      String customerTable,
                                      String orderCladdingTable,
                                      String orderRoofingTable,
                                      String shedTable,
                                      String shedCladdingTable,
                                      String shedFlooringsTable,
                                      String offerEmployeeTable) throws SQLException
    {
        Offer offer = createOffer(
                resultSet, offerTable, orderTable, customerTable, orderCladdingTable, orderRoofingTable, shedTable,
                shedCladdingTable, shedFlooringsTable, offerEmployeeTable);
        Employee employee = createEmployee(purchaseEmployeeTable, resultSet);
        Bom      bom      = null;

        return new PurchaseRecord(resultSet.getInt(table + ".id"), offer.getId(), offer, employee.getId(), employee, null, null, resultSet.getTimestamp(table + ".created_at").toLocalDateTime());
    }
}
