package tvestergaard.fog.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.Shed;
import tvestergaard.fog.data.sheds.ShedRecord;

import static org.junit.Assert.assertEquals;

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
    private static Cladding cladding2;
    private static Roofing  roofing1;
    private static Roofing  roofing2;
    private static Shed     shed1;

    private Order order1;
    private Order order2;

    @BeforeClass
    public static void before() throws Exception
    {
        customer1 = customerDAO.create("name1", "address1", "email1", "phone1", "password1", Customer.ContactMethod.EMAIL, true);
        customer2 = customerDAO.create("name2", "address2", "email2", "phone2", "password2", Customer.ContactMethod.PHONE, false);

        cladding1 = claddingDAO.create("name1", "description1", 1, true);
        cladding1 = claddingDAO.create("name2", "description2", 2, false);

        roofing1 = roofingDAO.create("name1", "description1", 1, 2, 4, true);
        roofing2 = roofingDAO.create("name2", "description2", 10, 6, 5, false);

        shed1 = new ShedRecord(0, 1, 2, cladding1, flooringDAO.create("name1", "description1", 0, true));
    }

    @Before
    public void setUp() throws Exception
    {
        this.order1 = dao.create(customer1, Order.Type.GARAGE, cladding1, 1, 2, 3, roofing1, 6, Order.Rafters.PREMADE, shed1);
        this.order2 = dao.create(customer2, Order.Type.SHED, cladding1, 10, 11, 12, roofing2, 34, Order.Rafters.SELFMADE, null);
    }

    @Test
    public void create() throws Exception
    {
        Customer      expectedCustomer = customer1;
        Order.Type    expectedType     = Order.Type.GARAGE;
        Cladding      expectedCladding = cladding2;
        int           expectedWidth    = 5;
        int           expectedLength   = 10;
        int           expectedHeight   = 15;
        Roofing       expectedRoofing  = roofing2;
        int           expectedSlope    = 20;
        Order.Rafters expectedRafters  = Order.Rafters.SELFMADE;
        Shed          expectedShed     = shed1;

        Order actual = dao.create(expectedCustomer, expectedType, expectedCladding, expectedWidth, expectedLength,
                                  expectedHeight, expectedRoofing, expectedSlope, expectedRafters, expectedShed);

        assertEquals(expectedCustomer, actual.getCustomer());
        assertEquals(expectedType, actual.getType());
        assertEquals(expectedWidth, actual.getWidth());
        assertEquals(expectedLength, actual.getLength());
        assertEquals(expectedHeight, actual.getHeight());
        assertEquals(expectedRoofing, actual.getRoofing());
        assertEquals(expectedSlope, actual.getSlope());
        assertEquals(expectedRafters, actual.getRafters());
        assertEquals(expectedShed, actual.getShed());
    }

    @Test
    public void get() throws Exception
    {

    }
}