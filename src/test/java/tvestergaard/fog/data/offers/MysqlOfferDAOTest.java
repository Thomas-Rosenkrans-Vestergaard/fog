package tvestergaard.fog.data.offers;

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
import tvestergaard.fog.data.orders.MysqlOrderDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderBlueprint;
import tvestergaard.fog.data.orders.ShedBlueprint;
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
import static tvestergaard.fog.Helpers.randomInt;
import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.offers.OfferColumn.ID;

public class MysqlOfferDAOTest
{

    private static final MysqlDataSource  source      = TestDataSource.getSource();
    private static final MysqlCustomerDAO customerDAO = new MysqlCustomerDAO(source);
    private static final MysqlEmployeeDAO employeeDAO = new MysqlEmployeeDAO(source);
    private static final MysqlCladdingDAO claddingDAO = new MysqlCladdingDAO(source);
    private static final MysqlRoofingDAO  roofingDAO  = new MysqlRoofingDAO(source);
    private static final MysqlFlooringDAO flooringDAO = new MysqlFlooringDAO(source);
    private static final MysqlOrderDAO    orderDAO    = new MysqlOrderDAO(source);
    private static final MysqlOfferDAO    offerDAO    = new MysqlOfferDAO(source);

    private static Customer customer1;
    private static Employee employee1;
    private static Cladding cladding1;
    private static Roofing  roofing1;
    private static Flooring flooring1;
    private static Order    order1;
    private        Offer    offer1;

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
        offer1 = offerDAO.create(OfferBlueprint.from(order1.getId(), employee1.getId(), 10000 * 100));
    }

    @After
    public void tearDown() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

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
        List<Offer> offers = offerDAO.get();
        assertEquals(1, offers.size());
        assertEquals(offer1, offers.get(0));
    }

    @Test
    public void first() throws Exception
    {
        Offer actual = offerDAO.first(where(eq(ID, offer1.getId())));
        assertEquals(offer1, actual);
        assertNull(offerDAO.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        int expectedPrice = randomInt(1);

        Offer offer = offerDAO.create(OfferBlueprint.from(order1.getId(), employee1.getId(), expectedPrice));
        assertEquals(order1, offer.getOrder());
        assertEquals(employee1, offer.getEmployee());
        assertEquals(expectedPrice, offer.getPrice());
        assertEquals(OfferStatus.OPEN, offer.getStatus());
    }

    @Test
    public void reject() throws Exception
    {
        assertEquals(OfferStatus.OPEN, offer1.getStatus());
        offerDAO.reject(offer1.getId());

        Offer expected = offerDAO.first(where(eq(ID, offer1.getId())));
        assertEquals(OfferStatus.REJECTED, expected.getStatus());
    }

    @Test
    public void getNumberOfOpenOffers() throws Exception
    {
        assertEquals(1, offerDAO.getNumberOfOpenOffers(customer1.getId()));
        offerDAO.create(OfferBlueprint.from(order1.getId(), employee1.getId(), 10000 * 100));
        assertEquals(2, offerDAO.getNumberOfOpenOffers(customer1.getId()));
    }
}