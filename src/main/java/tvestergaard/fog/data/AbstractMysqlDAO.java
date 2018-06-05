package tvestergaard.fog.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingRecord;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentDefinitionRecord;
import tvestergaard.fog.data.components.ComponentRecord;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerRecord;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeRecord;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringRecord;
import tvestergaard.fog.data.materials.*;
import tvestergaard.fog.data.materials.attributes.*;
import tvestergaard.fog.data.models.Model;
import tvestergaard.fog.data.models.ModelRecord;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferRecord;
import tvestergaard.fog.data.offers.OfferStatus;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderRecord;
import tvestergaard.fog.data.orders.Shed;
import tvestergaard.fog.data.orders.ShedRecord;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseRecord;
import tvestergaard.fog.data.purchases.bom.BomDrawing;
import tvestergaard.fog.data.purchases.bom.BomDrawingRecord;
import tvestergaard.fog.data.purchases.bom.BomLine;
import tvestergaard.fog.data.purchases.bom.BomLineRecord;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingRecord;
import tvestergaard.fog.data.roofing.RoofingType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.data.materials.attributes.DataType.STRING;

public abstract class AbstractMysqlDAO
{

    /**
     * The {@link MysqlDataSource} to perform operations upon.
     */
    private final MysqlDataSource source;

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
        Connection connection = source.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Creates a new {@link BomLine} from the provided information.
     *
     * @param resultSet The result set containing the information.
     * @param tLine     The name of the table containing the bom line.
     * @param tMaterial The name of the table containing the material.
     * @param tCategory The name of the table containing the category.
     * @return The resulting {@link BomLine}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected BomLine createBomLine(ResultSet resultSet, String tLine, String tMaterial, String tCategory)
            throws SQLException
    {
        SimpleMaterial material = createSimpleMaterial(resultSet, tMaterial, tCategory);

        return new BomLineRecord(
                resultSet.getInt(tLine + ".id"),
                material,
                material.getId(),
                resultSet.getInt(tLine + ".amount"),
                resultSet.getString(tLine + ".notes")
        );
    }


    /**
     * Creates a new {@link BomDrawing} from the provided information.
     *
     * @param results  The result set containing the information.
     * @param tDrawing The name of the table containing the drawing.
     * @return The resulting {@link BomDrawing}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected BomDrawing createBomDrawing(ResultSet results, String tDrawing) throws SQLException
    {
        return new BomDrawingRecord(
                results.getInt(tDrawing + ".id"),
                results.getString(tDrawing + ".title"),
                results.getString(tDrawing + ".content")
        );
    }

    /**
     * Creates a new {@link Cladding} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tCladding The name of the table containing the cladding.
     * @return The resulting {@link Cladding}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Cladding createCladding(ResultSet results, String tCladding) throws SQLException
    {
        return new CladdingRecord(
                results.getInt(tCladding + ".id"),
                results.getString(tCladding + ".name"),
                results.getString(tCladding + ".description"),
                results.getBoolean(tCladding + ".active")
        );
    }

    /**
     * Creates a new {@link Customer} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tCustomer The name of the table containing the customer.
     * @return The resulting {@link Customer}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Customer createCustomer(ResultSet results, String tCustomer) throws SQLException
    {
        return new CustomerRecord(
                results.getInt(tCustomer + ".id"),
                results.getString(tCustomer + ".name"),
                results.getString(tCustomer + ".address"),
                results.getString(tCustomer + ".email"),
                results.getString(tCustomer + ".phone"),
                results.getString(tCustomer + ".password"),
                results.getBoolean(tCustomer + ".active"),
                results.getBoolean(tCustomer + ".verified"),
                results.getTimestamp(tCustomer + ".created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Flooring} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tFlooring The name of the table containing the flooring.
     * @return The resulting {@link Flooring}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Flooring createFlooring(ResultSet results, String tFlooring) throws SQLException
    {
        return new FlooringRecord(
                results.getInt(tFlooring + ".id"),
                results.getString(tFlooring + ".name"),
                results.getString(tFlooring + ".description"),
                results.getBoolean(tFlooring + ".active")
        );
    }

    /**
     * Creates a new {@link Roofing} from the provided information.
     *
     * @param results  The result set containing the information.
     * @param roofings The name of the table containing the roofing.
     * @return The resulting {@link Roofing}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Roofing createRoofing(ResultSet results, String roofings) throws SQLException
    {
        return new RoofingRecord(
                results.getInt(roofings + ".id"),
                results.getString(roofings + ".name"),
                results.getString(roofings + ".description"),
                results.getBoolean(roofings + ".active"),
                RoofingType.valueOf(results.getString(roofings + ".type"))
        );
    }

    /**
     * Creates a new {@link Shed} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tShed     The name of the table containing the shed.
     * @param tCladding The name of the table containing the cladding.
     * @param tFlooring The name of the table containing the flooring.
     * @return The resulting {@link Shed}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Shed createShed(ResultSet results, String tShed, String tCladding, String tFlooring) throws SQLException
    {
        return new ShedRecord(
                results.getInt(tShed + ".id"),
                results.getInt(tShed + ".depth"),
                results.getInt(tShed + ".cladding"),
                createCladding(results, tCladding),
                results.getInt(tShed + ".flooring"),
                createFlooring(results, tFlooring)
        );
    }

    /**
     * Creates a new {@link Employee} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tEmployee The name of the table containing the employee.
     * @return The resulting {@link Employee}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Employee createEmployee(ResultSet results, String tEmployee) throws SQLException
    {
        return new EmployeeRecord(
                results.getInt(tEmployee + ".id"),
                results.getString(tEmployee + ".name"),
                results.getString(tEmployee + ".username"),
                results.getString(tEmployee + ".password"),
                results.getBoolean(tEmployee + ".active"),
                createRoleSet(results, tEmployee + ".roles"),
                results.getTimestamp(tEmployee + ".created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new set of roles using the provided information.
     *
     * @param results The result set containing the information.
     * @param column  The name of the column containing the comma separated roles of the employees.
     * @return The resulting set of roles.
     * @throws SQLException When the factory could not access a required column.
     */
    private Set<Role> createRoleSet(ResultSet results, String column) throws SQLException
    {
        String roles = results.getString(column);
        if (roles == null)
            return new HashSet<>();

        String[]  strings = roles.split(",");
        Set<Role> result  = new HashSet<>();
        for (String string : strings)
            result.add(Role.valueOf(string));

        return result;
    }

    /**
     * Creates a new {@link SimpleMaterial}. A simple material is a material without the attributes.
     *
     * @param results   The result set containing the information.
     * @param tMaterial The name of the table containing the material.
     * @param tCategory The name of the table containing the category.
     * @return The resulting {@link SimpleMaterial}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected SimpleMaterial createSimpleMaterial(ResultSet results, String tMaterial, String tCategory) throws SQLException
    {
        return new MaterialRecord(
                results.getInt(tMaterial + ".id"),
                results.getString(tMaterial + ".number"),
                results.getString(tMaterial + ".description"),
                results.getInt(tMaterial + ".price"),
                results.getInt(tMaterial + ".unit"),
                results.getBoolean(tMaterial + ".active"),
                results.getInt(tCategory + ".id"),
                createCategory(results, tCategory),
                new HashSet<>()
        );
    }

    /**
     * Creates a new {@link Material} from the provided information.
     *
     * @param results              The result set containing the information.
     * @param tMaterial            The name of the table containing the material.
     * @param tCategory            The name of the table containing the category.
     * @param attributes           The result set containing the attributes of the material to create.
     * @param tAttributeDefinition The name of the table containing the attribute definitions.
     * @param tAttributeValues     The name of the table containing the attribute values.
     * @return The resulting {@link Material}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Material createMaterial(ResultSet results,
                                      String tMaterial,
                                      String tCategory,
                                      ResultSet attributes,
                                      String tAttributeDefinition,
                                      String tAttributeValues) throws SQLException
    {
        return new MaterialRecord(
                results.getInt(tMaterial + ".id"),
                results.getString(tMaterial + ".number"),
                results.getString(tMaterial + ".description"),
                results.getInt(tMaterial + ".price"),
                results.getInt(tMaterial + ".unit"),
                results.getBoolean(tMaterial + ".active"),
                results.getInt(tCategory + ".id"),
                createCategory(results, tCategory),
                createAttributeSet(attributes, tAttributeDefinition, tAttributeValues)
        );
    }

    /**
     * Creates a new {@link Category} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tCategory The name of the table containing the category.
     * @return The resulting {@link Category}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Category createCategory(ResultSet results, String tCategory) throws SQLException
    {
        return new CategoryRecord(
                results.getInt(tCategory + ".id"),
                results.getString(tCategory + ".name")
        );
    }

    /**
     * Creates a new set of {@link Attribute}s.
     *
     * @param results               The result set containing the information.
     * @param tAttributesDefinition The name of the table containing the attribute definitions.
     * @param tAttributeValues      The name of the table containing the attribute values.
     * @return The resulting set of {@link Attribute}s.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Set<Attribute> createAttributeSet(ResultSet results, String tAttributesDefinition, String tAttributeValues)
            throws SQLException
    {
        Set<Attribute> result = new HashSet<>();
        while (results.next())
            result.add(createAttribute(results, tAttributesDefinition, tAttributeValues));

        return result;
    }

    /**
     * Creates a new {@link AttributeDefinition} from the provided information.
     *
     * @param results              The result set containing the information.
     * @param tAttributeDefinition The name of the table containing the attribute definitions.
     * @return The resulting {@link AttributeDefinition}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected AttributeDefinition createAttributeDefinition(ResultSet results, String tAttributeDefinition)
            throws SQLException
    {
        return new DefaultAttributeDefinition(
                results.getInt(tAttributeDefinition + ".id"),
                results.getString(tAttributeDefinition + ".name"),
                DataType.valueOf(results.getString(tAttributeDefinition + ".data_type"))
        );
    }

    /**
     * Creates a new {@link Attribute} from the provided information.
     *
     * @param results              The result set containing the information.
     * @param tAttributeDefinition The name of the table containing the attribute definitions.
     * @param tAttributeValues     The name of the table containing the attributes values.
     * @return The resulting {@link Attribute}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Attribute createAttribute(ResultSet results, String tAttributeDefinition, String tAttributeValues) throws SQLException
    {
        AttributeDefinition definition = createAttributeDefinition(results, tAttributeDefinition);

        return new DefaultAttribute(
                createAttributeDefinition(results, tAttributeDefinition),
                getAttributeValue(results, tAttributeValues, definition.getDataType())
        );
    }

    /**
     * Creates am object value from the provided {@link DataType} and result set.
     *
     * @param results         The result set containing the information.
     * @param tAttributeValue The name of the table containing the attribute values.
     * @param attributeType   The data type to return the value as.
     * @return The object value.
     * @throws SQLException When the factory could not access a required column.
     */
    private Object getAttributeValue(ResultSet results, String tAttributeValue, DataType attributeType) throws SQLException
    {

        // Check for null value.
        Object o = results.getObject(tAttributeValue + ".value");
        if (o == null)
            return null;

        if (attributeType == DataType.INT)
            return results.getInt(tAttributeValue + ".value");

        if (attributeType == STRING)
            return results.getString(tAttributeValue + ".value");

        throw new IllegalStateException("Unknown data type " + attributeType.name());
    }

    /**
     * Sets the parameter with the provided index to the value in the provided {@link Attribute}.
     *
     * @param statement      The statement to set the parameter of.
     * @param parameterIndex The index of the parameter to set on the statement.
     * @param attribute      The attribute containing the value to set.
     * @throws SQLException When the factory could not access a required column.
     */
    protected void setAttributeValue(PreparedStatement statement, int parameterIndex, Attribute attribute)
            throws SQLException
    {
        DataType dataType = attribute.getDefinition().getDataType();

        if (dataType == DataType.INT) {
            statement.setInt(parameterIndex, attribute.getInt());
            return;
        }

        if (dataType == DataType.STRING) {
            statement.setString(parameterIndex, attribute.getString());
            return;
        }

        throw new IllegalStateException("Unknown data type " + dataType.name());
    }

    /**
     * Creates a new {@link ComponentDefinition} from the provided information.
     *
     * @param results              The result set containing the information.
     * @param tComponentDefinition The name of the table containing the component definitions.
     * @param tCategory            The name of the table containing the category.
     * @return The resulting {@link ComponentDefinition}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected ComponentDefinition createComponentDefinition(ResultSet results, String tComponentDefinition, String tCategory)
            throws SQLException
    {

        Category category = createCategory(results, tCategory);

        return new ComponentDefinitionRecord(
                results.getInt(tComponentDefinition + ".id"),
                results.getString(tComponentDefinition + ".identifier"),
                results.getString(tComponentDefinition + ".notes"),
                category.getId(),
                category
        );
    }

    /**
     * Creates a new {@link Component} from the provided information.
     *
     * @param results              The result set containing the information.
     * @param tComponentDefinition The name of the table containing the component definitions.
     * @param tMaterial            The name of the table containing the material.
     * @param tCategory            The name of the table containing the category.
     * @param attributes           The result set containing the attributes for the material.
     * @param tAttributeDefinition The name of the table containing the attribute definition.
     * @param tAttributeValue      The name of the table containing the attribute value.
     * @return The resulting {@link Component}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Component createComponent(ResultSet results,
                                        String tComponentDefinition,
                                        String tMaterial,
                                        String tCategory,
                                        ResultSet attributes,
                                        String tAttributeDefinition,
                                        String tAttributeValue) throws SQLException
    {
        ComponentDefinition definition = createComponentDefinition(results, tComponentDefinition, tCategory);
        Material            material   = createMaterial(results, tMaterial, tCategory, attributes, tAttributeDefinition, tAttributeValue);

        return new ComponentRecord(
                definition.getId(),
                definition,
                material.getId(),
                material
        );
    }

    /**
     * Creates a new IN operand. For size 5 returns '(?,?,?,?,?)'.
     *
     * @param size The number of arguments to provided to the in.
     * @return The resulting in parameters.
     */
    protected String createIn(int size)
    {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < size; x++) {
            if (x != 0) builder.append(',');
            builder.append('?');
        }

        return builder.toString();
    }

    /**
     * Creates a new {@link Model} from the provided information.
     *
     * @param results The result set containing the information.
     * @param tModel  The name of the table containing the model.
     * @return The resulting {@link Model}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Model createModel(ResultSet results, String tModel) throws SQLException
    {
        return new ModelRecord(
                results.getInt(tModel + ".id"),
                results.getString(tModel + ".name")
        );
    }

    /**
     * Creates a new {@link Order} instance from the provided {@code ResultSet}.
     *
     * @param results   The result set containing the information.
     * @param tOrder    The name of the table containing the order.
     * @param tCustomer The name of the table containing the customer.
     * @param tRoofing  The name of the table containing the roofing on the order.
     * @param tShed     The name of the table containing the shed.
     * @param tCladding The name of the table containing the cladding on the shed.
     * @param tFlooring The name of the table containing the flooring of the shed.
     * @return The resulting {@link Order}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Order createOrder(ResultSet results,
                                String tOrder,
                                String tCustomer,
                                String tRoofing,
                                String tShed,
                                String tCladding,
                                String tFlooring) throws SQLException
    {
        Shed shed = results.getInt("shed") == 0 ? null : createShed(results, tShed, tCladding, tFlooring);

        return new OrderRecord(
                results.getInt(tOrder + ".id"),
                results.getInt(tOrder + ".customer"),
                createCustomer(results, tCustomer),
                results.getInt(tOrder + ".width"),
                results.getInt(tOrder + ".length"),
                results.getInt(tOrder + ".height"),
                results.getInt(tOrder + ".roofing"),
                createRoofing(results, tRoofing),
                results.getInt(tOrder + ".slope"),
                shed,
                shed,
                shed,
                results.getString(tOrder + ".comment"),
                results.getBoolean(tOrder + ".active"),
                results.getInt(tOrder + ".offers"),
                results.getInt(tOrder + ".open_offers"),
                results.getTimestamp(tOrder + ".created_at").toLocalDateTime()
        );
    }

    /**
     * Creates a new {@link Offer} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tOffer    The name of the table containing the offer.
     * @param tEmployee The name of the table containing the employee who created the offer.
     * @param tOrder    The name of the table containing the order.
     * @param tCustomer The name of the table containing the customer who created the order.
     * @param tRoofing  The name of the table containing the roofing of the order.
     * @param tShed     The name of the table containing the shed of the order.
     * @param tCladding The name of the table containing the cladding on the shed.
     * @param tFlooring The name of the table containing the flooring of the shed.
     * @return The resulting {@link Offer}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Offer createOffer(ResultSet results,
                                String tOffer,
                                String tEmployee,
                                String tOrder,
                                String tCustomer,
                                String tRoofing,
                                String tShed,
                                String tCladding,
                                String tFlooring) throws SQLException
    {
        Order    order    = createOrder(results, tOrder, tCustomer, tRoofing, tShed, tCladding, tFlooring);
        Employee employee = createEmployee(results, tEmployee);

        return new OfferRecord(
                results.getInt(tOffer + ".id"),
                order,
                order.getId(),
                employee,
                employee.getId(),
                results.getInt(tOffer + ".price"),
                OfferStatus.valueOf(results.getString(tOffer + ".status")),
                results.getTimestamp(tOffer + ".created_at").toLocalDateTime());
    }

    /**
     * Creates a new {@link Customer} from the provided information.
     *
     * @param results   The result set containing the information.
     * @param tPurchase The name of the table containing the purchase.
     * @param tOffer    The name of the table containing the offer.
     * @param tEmployee The name of the table containing the employee who placed the offer.
     * @param tOrder    The name of the table containing the order.
     * @param tCustomer The name of the table containing the customer who placed the order.
     * @param tRoofing  The name of the table containing the roofing on the order.
     * @param tShed     The name of the table containing the shed of the order.
     * @param tCladding The name of the table containing the cladding on the shed.
     * @param tFlooring The name of the table containing the flooring of the shed.
     * @return The resulting {@link Purchase}.
     * @throws SQLException When the factory could not access a required column.
     */
    protected Purchase createPurchase(ResultSet results,
                                      String tPurchase,
                                      String tOffer,
                                      String tEmployee,
                                      String tOrder,
                                      String tCustomer,
                                      String tRoofing,
                                      String tShed,
                                      String tCladding,
                                      String tFlooring) throws SQLException
    {
        Offer offer = createOffer(
                results,
                tOffer,
                tEmployee,
                tOrder,
                tCustomer,
                tRoofing,
                tShed,
                tCladding,
                tFlooring);

        return new PurchaseRecord(
                results.getInt(tPurchase + ".id"),
                offer.getId(),
                offer,
                results.getInt(tPurchase + ".bom"),
                results.getTimestamp(tPurchase + ".created_at").toLocalDateTime());
    }
}
