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
import tvestergaard.fog.data.models.ModelDAO;
import tvestergaard.fog.data.models.MysqlModelDAO;
import tvestergaard.fog.data.offers.MysqlOfferDAO;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.orders.MysqlOrderDAO;
import tvestergaard.fog.data.orders.OrderDAO;
import tvestergaard.fog.data.purchases.MysqlPurchaseDAO;
import tvestergaard.fog.data.purchases.PurchaseDAO;
import tvestergaard.fog.data.purchases.bom.BomDAO;
import tvestergaard.fog.data.purchases.bom.MysqlBomDAO;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.data.tokens.MysqlTokenDAO;
import tvestergaard.fog.data.tokens.TokenDAO;
import tvestergaard.fog.logic.ModelFacade;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.email.CurrentEmailTemplate;
import tvestergaard.fog.logic.email.SimpleJavaMailer;
import tvestergaard.fog.logic.employees.EmployeeFacade;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.materials.MaterialFacade;
import tvestergaard.fog.logic.offers.OfferFacade;
import tvestergaard.fog.logic.orders.OrderFacade;
import tvestergaard.fog.logic.orders.OrderPlacer;
import tvestergaard.fog.logic.purchases.PurchaseFacade;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.logic.tokens.TokenAuthenticator;
import tvestergaard.fog.logic.tokens.TokenGenerator;
import tvestergaard.fog.logic.tokens.TokenIssuer;

public class Facades
{

    private static final ApplicationMailer mailer = new SimpleJavaMailer(new CurrentEmailTemplate());

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
    private static final PurchaseDAO     purchaseDAO = new MysqlPurchaseDAO(source);
    private static final ModelDAO        modelDAO    = new MysqlModelDAO(source);
    private static final BomDAO          bomDAO      = new MysqlBomDAO(source);


    private static final TokenIssuer        tokenIssuer        = new TokenIssuer(tokenDAO, new TokenGenerator());
    private static final TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(tokenDAO, 24);

    public static final CladdingFacade     claddingFacade     = new CladdingFacade(claddingDAO);
    public static final CustomerFacade     customerFacade     = new CustomerFacade(customerDAO, tokenDAO, mailer);
    public static final EmployeeFacade     employeeFacade     = new EmployeeFacade(employeeDAO);
    public static final FlooringFacade     flooringFacade     = new FlooringFacade(flooringDAO);
    public static final MaterialFacade     materialFacade     = new MaterialFacade(materialDAO);
    public static final OrderFacade        orderFacade        = new OrderFacade(new OrderPlacer(orderDAO, customerDAO, mailer), orderDAO);
    public static final RoofingFacade      roofingFacade      = new RoofingFacade(roofingDAO);
    public static final OfferFacade        offerFacade        = new OfferFacade(offerDAO, orderDAO, employeeDAO, mailer, tokenIssuer, tokenAuthenticator);
    public static final ConstructionFacade constructionFacade = new ConstructionFacade(modelDAO, roofingDAO);
    public static final PurchaseFacade     purchaseFacade     = new PurchaseFacade(purchaseDAO, offerDAO, bomDAO, constructionFacade);
    public static final ModelFacade        skeletonFacade     = new ModelFacade(modelDAO);
}
