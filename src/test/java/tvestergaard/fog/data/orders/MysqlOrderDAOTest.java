package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.*;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingBlueprint;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringBlueprint;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingBlueprint;
import tvestergaard.fog.data.roofing.RoofingType;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tvestergaard.fog.data.constraints.Constraint.eq;

public class MysqlOrderDAOTest
{

    private static final MysqlDataSource  source      = TestDataSource.getSource();
    private static final MysqlCustomerDAO customerDAO = new MysqlCustomerDAO(source);
    private static final MysqlCladdingDAO claddingDAO = new MysqlCladdingDAO(source);
    private static final MysqlRoofingDAO  roofingDAO  = new MysqlRoofingDAO(source);
    private static final MysqlFlooringDAO flooringDAO = new MysqlFlooringDAO(source);
    private static final MysqlOrderDAO    dao         = new MysqlOrderDAO(source);

    private static Customer customer1;
    private static Customer customer2;
    private static Cladding cladding1;
    private static Roofing  roofing1;
    private static Roofing  roofing2;
    private static Flooring flooring1;

    private Order order1;
    private Order order2;

    @BeforeClass
    public static void before() throws Exception
    {
        customer1 = customerDAO.create(CustomerBlueprint.from("name1", "address1", "email1", "phone1", "password1", true));
        customer2 = customerDAO.create(CustomerBlueprint.from("name2", "address2", "email2", "phone2", "password2", false));

        cladding1 = claddingDAO.create(CladdingBlueprint.from("name1", "description1", true));

        roofing1 = roofingDAO.create(RoofingBlueprint.from("name1", "description1", true, RoofingType.TILED), new ArrayList<>());
        roofing2 = roofingDAO.create(RoofingBlueprint.from("name2", "description2", false, RoofingType.TILED), new ArrayList<>());

        flooring1 = flooringDAO.create(FlooringBlueprint.from("name1", "description1", true));
    }

    @Before
    public void setUp() throws Exception
    {
        ShedBlueprint shedBlueprint = ShedBlueprint.from(10, cladding1.getId(), flooring1.getId());
        this.order1 = dao.create(OrderBlueprint.from(customer1.getId(), 1, 2, 3, roofing1.getId(), 6, RafterChoice.PREBUILT, true, shedBlueprint, "Some comment"));
        this.order2 = dao.create(OrderBlueprint.from(customer2.getId(), 10, 11, 12, roofing2.getId(), 34, RafterChoice.BUILD_SELF, false, null, "Some comment"));
    }

    @After
    public void tearDown() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();

        connection.createStatement().executeUpdate("DELETE FROM orders");
        connection.createStatement().executeUpdate("DELETE FROM sheds");
    }

    @AfterClass
    public static void afterClass() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM tokens");
        connection.createStatement().executeUpdate("DELETE FROM customers");
        connection.createStatement().executeUpdate("DELETE FROM claddings");
        connection.createStatement().executeUpdate("DELETE FROM roofings");
    }

    @Test
    public void create() throws Exception
    {
        Customer      expectedCustomer = customer1;
        int           expectedWidth    = 5;
        int           expectedLength   = 10;
        int           expectedHeight   = 15;
        Roofing       expectedRoofing  = roofing2;
        int           expectedSlope    = 20;
        RafterChoice  expectedRafters  = RafterChoice.BUILD_SELF;
        boolean       expectedActive   = true;
        ShedBlueprint expectedShed     = ShedBlueprint.from(200, cladding1.getId(), flooring1.getId());

        Order actual = dao.create(OrderBlueprint.from(expectedCustomer.getId(), expectedWidth, expectedLength,
                expectedHeight, expectedRoofing.getId(), expectedSlope, expectedRafters, expectedActive, expectedShed, "Some comment"));

        assertEquals(expectedCustomer, actual.getCustomer());
        assertEquals(expectedWidth, actual.getWidth());
        assertEquals(expectedLength, actual.getLength());
        assertEquals(expectedHeight, actual.getHeight());
        assertEquals(expectedRoofing, actual.getRoofing());
        assertEquals(expectedSlope, actual.getSlope());
        assertEquals(expectedRafters, actual.getRafterChoice());
        assertEquals(expectedShed.getDepth(), actual.getShed().getDepth());
        assertEquals(cladding1, actual.getShed().getCladding());
        assertEquals(flooring1, actual.getShed().getFlooring());
        assertTrue(actual.isActive());
        assertEquals(0, actual.getNumberOfOffers());
        assertEquals(0, actual.getNumberOfOpenOffers());
    }

    @Test
    public void get() throws Exception
    {
        Order actual = dao.first(Constraint.where(eq(OrderColumn.ID, order1.getId())));
        assertEquals(order1, actual);
    }

    @Test
    public void update() throws Exception
    {

    }

    @Test
    public void getNumberOfNewOrders() throws Exception
    {
        assertEquals(2, dao.getNumberOfNewOrders());
        dao.create(OrderBlueprint.from(customer1.getId(), 1, 2, 3, roofing1.getId(), 4, RafterChoice.BUILD_SELF, true, null, "Some comment"));
        assertEquals(3, dao.getNumberOfNewOrders());
    }
}