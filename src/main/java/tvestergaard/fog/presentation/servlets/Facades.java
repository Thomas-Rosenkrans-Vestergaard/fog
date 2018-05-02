package tvestergaard.fog.presentation.servlets;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.MysqlCustomerDAO;
import tvestergaard.fog.data.employees.EmployeeDAO;
import tvestergaard.fog.data.employees.MysqlEmployeeDAO;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;
import tvestergaard.fog.data.materials.MaterialDAO;
import tvestergaard.fog.data.materials.MysqlMaterialDAO;
import tvestergaard.fog.data.offers.MysqlOfferDAO;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.orders.MysqlOrderDAO;
import tvestergaard.fog.data.orders.OrderDAO;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.data.tokens.MysqlTokenDAO;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.employees.EmployeeFacade;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.roofings.RoofingFacade;

public class Facades
{

    private static final MysqlDataSource source      = ProductionDataSource.getSource();
    private static final CladdingDAO     claddingDAO = new MysqlCladdingDAO(source);
    private static final CustomerDAO     customerDAO = new MysqlCustomerDAO(source);
    private static final TokenDAO        tokenDAO    = new MysqlTokenDAO(source);
    private static final EmployeeDAO     employeeDAO = new MysqlEmployeeDAO(source);
    private static final FlooringDAO     flooringDAO = new MysqlFlooringDAO(source);
    private static final MaterialDAO     materialDAO = new MysqlMaterialDAO(source);
    private static final OrderDAO        orderDAO    = new MysqlOrderDAO(source);
    private static final RoofingDAO      roofingDAO  = new MysqlRoofingDAO(source);
    private static final OfferDAO        offerDAO    = new MysqlOfferDAO(source);

    public static final CladdingFacade claddingFacade = new CladdingFacade(claddingDAO);
    public static final CustomerFacade customerFacade = new CustomerFacade(customerDAO, tokenDAO);
    public static final EmployeeFacade employeeFacade = new EmployeeFacade(employeeDAO);
    public static final FlooringFacade flooringFacade = new FlooringFacade(flooringDAO);
    public static final MaterialFacade materialFacade = new MaterialFacade(materialDAO);
    public static final OrderFacade    orderFacade    = new OrderFacade(orderDAO);
    public static final RoofingFacade  roofingFacade  = new RoofingFacade(roofingDAO);
    public static final OfferFacade    offerFacade    = new OfferFacade(offerDAO);
}
