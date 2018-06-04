package tvestergaard.fog.data.purchases;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.*;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingBlueprint;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeBlueprint;
import tvestergaard.fog.data.employees.MysqlEmployeeDAO;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringBlueprint;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.materials.MysqlMaterialDAO;
import tvestergaard.fog.data.offers.MysqlOfferDAO;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferBlueprint;
import tvestergaard.fog.data.offers.OfferStatus;
import tvestergaard.fog.data.orders.MysqlOrderDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderBlueprint;
import tvestergaard.fog.data.orders.ShedBlueprint;
import tvestergaard.fog.data.purchases.bom.BomBlueprint;
import tvestergaard.fog.data.purchases.bom.BomDrawingBlueprint;
import tvestergaard.fog.data.purchases.bom.BomLineBlueprint;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingBlueprint;
import tvestergaard.fog.data.roofing.RoofingType;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.purchases.PurchaseColumn.ID;

public class MysqlPurchaseDAOTest
{

    private static final MysqlDataSource  source      = TestDataSource.getSource();
    private static final MysqlCustomerDAO customerDAO = new MysqlCustomerDAO(source);
    private static final MysqlEmployeeDAO employeeDAO = new MysqlEmployeeDAO(source);
    private static final MysqlCladdingDAO claddingDAO = new MysqlCladdingDAO(source);
    private static final MysqlRoofingDAO  roofingDAO  = new MysqlRoofingDAO(source);
    private static final MysqlFlooringDAO flooringDAO = new MysqlFlooringDAO(source);
    private static final MysqlOrderDAO    orderDAO    = new MysqlOrderDAO(source);
    private static final MysqlOfferDAO    offerDAO    = new MysqlOfferDAO(source);
    private static final PurchaseDAO      purchaseDAO = new MysqlPurchaseDAO(source);

    private static Customer customer1;
    private static Employee employee1;
    private static Cladding cladding1;
    private static Roofing  roofing1;
    private static Flooring flooring1;
    private static Order    order1;
    private static Offer    offer1;
    private        Purchase purchase1;

    @BeforeClass
    public static void before() throws Exception
    {
        customer1 = customerDAO.create(CustomerBlueprint.from("name1", "address1", "email1", "phone1", "password1", true));
        employee1 = employeeDAO.create(EmployeeBlueprint.from("Name", "Username", "Password", new HashSet<>(), true));
        cladding1 = claddingDAO.create(CladdingBlueprint.from("name1", "description1", true));
        roofing1 = roofingDAO.create(RoofingBlueprint.from("name1", "description1", true, RoofingType.TILED), new ArrayList<>());
        flooring1 = flooringDAO.create(FlooringBlueprint.from("name1", "description1", true));
        ShedBlueprint shedBlueprint = ShedBlueprint.from(10, cladding1.getId(), flooring1.getId());
        order1 = orderDAO.create(OrderBlueprint.from(customer1.getId(), 1, 2, 3, roofing1.getId(), 6, true, shedBlueprint, "Some comment"));
    }

    @Before
    public void setUp() throws Exception
    {
        BomBlueprint bomBlueprint = BomBlueprint.from(new ArrayList<>(), new ArrayList<>());
        offer1 = offerDAO.create(OfferBlueprint.from(order1.getId(), employee1.getId(), 10000 * 100));
        purchase1 = purchaseDAO.create(PurchaseBlueprint.from(offer1.getId()), bomBlueprint);
    }

    @After
    public void tearDown() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("DELETE FROM bom_drawings");
        connection.createStatement().executeUpdate("DELETE FROM bom_lines");
        connection.createStatement().executeUpdate("DELETE FROM purchases");
        connection.createStatement().executeUpdate("DELETE FROM bom");
        connection.createStatement().executeUpdate("DELETE FROM offers");
    }

    @AfterClass
    public static void afterClass() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM orders");
        connection.createStatement().executeUpdate("DELETE FROM sheds");
        connection.createStatement().executeUpdate("DELETE FROM tokens");
        connection.createStatement().executeUpdate("DELETE FROM customers");
        connection.createStatement().executeUpdate("DELETE FROM employees");
        connection.createStatement().executeUpdate("DELETE FROM claddings");
        connection.createStatement().executeUpdate("DELETE FROM roofings");
    }

    @Test
    public void get() throws Exception
    {
        List<Purchase> actual = purchaseDAO.get(null);
        assertEquals(1, actual.size());
        assertEquals(purchase1, actual.get(0));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(purchase1, purchaseDAO.first(where(eq(ID, purchase1.getId()))));
        assertNull(purchaseDAO.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        List<BomLineBlueprint>    lineBlueprints    = new ArrayList<>();
        List<BomDrawingBlueprint> drawingBlueprints = new ArrayList<>();

        // Must created new offer, since the offer column in purchases is unique
        Offer    offer    = offerDAO.create(OfferBlueprint.from(order1.getId(), employee1.getId(), 10000 * 100));
        Purchase purchase = purchaseDAO.create(PurchaseBlueprint.from(offer.getId()), BomBlueprint.from(lineBlueprints, drawingBlueprints));

        // Check that the offer was accepted
        assertEquals(OfferStatus.OPEN, offer.getStatus());
        assertEquals(OfferStatus.ACCEPTED, purchase.getOffer().getStatus());

        assertEquals(offer.getCreatedAt(), purchase.getOffer().getCreatedAt());
        assertEquals(offer.getEmployee(), purchase.getOffer().getEmployee());
        assertEquals(offer.getOrder(), purchase.getOffer().getOrder());
        assertEquals(offer.getId(), purchase.getOffer().getId());
    }
}