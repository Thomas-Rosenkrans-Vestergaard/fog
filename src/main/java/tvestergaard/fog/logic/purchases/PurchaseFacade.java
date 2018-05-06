package tvestergaard.fog.logic.purchases;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeDAO;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseBlueprint;
import tvestergaard.fog.data.purchases.PurchaseColumn;
import tvestergaard.fog.data.purchases.PurchaseDAO;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.offers.OfferColumn.ID;

public class PurchaseFacade
{

    /**
     * The purchase dao to use to access the purchases in the application.
     */
    private final PurchaseDAO purchaseDAO;

    /**
     * The offer dao used to access the offers in the application.
     */
    private final OfferDAO offerDAO;

    /**
     * The employee dao used to access the employees in the application.
     */
    private final EmployeeDAO employeeDAO;

    /**
     * Creates a new {@link PurchaseFacade}.
     *
     * @param purchaseDAO The purchase dao to use to access the purchases in the application.
     * @param offerDAO    The offer dao used to access the offers in the application.
     * @param employeeDAO The employee dao used to access the employees in the application.
     */
    public PurchaseFacade(PurchaseDAO purchaseDAO, OfferDAO offerDAO, EmployeeDAO employeeDAO)
    {
        this.purchaseDAO = purchaseDAO;
        this.offerDAO = offerDAO;
        this.employeeDAO = employeeDAO;
    }

    /**
     * Returns the purchases in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting purchases.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Purchase> get(Constraint<PurchaseColumn>... constraints)
    {
        try {
            return purchaseDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first purchase matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first purchase matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Purchase first(Constraint<PurchaseColumn>... constraints)
    {
        try {
            return purchaseDAO.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Creates a new purchase from the offer with the provided id.
     *
     * @param offerId    The id of the offer from which to create the purchase.
     * @param employeeId The id of the employee creating the purchase.
     * @return An entity representing the new purchase.
     * @throws InactiveCustomerException        When the customer that the offer was issued to is inactive, and can therefor
     *                                          not create purchases.
     * @throws InsufficientPermissionsException When the employee attempting to create the purchase, is not a member of
     *                                          role {@link Role#SALESMAN}.
     */
    public Purchase create(int offerId, int employeeId) throws InactiveCustomerException, InsufficientPermissionsException
    {
        try {
            Employee employee = employeeDAO.first(where(eq(ID, employeeId)));
            if (!employee.is(Role.SALESMAN))
                throw new InsufficientPermissionsException(Role.SALESMAN);

            Offer offer = offerDAO.first(where(eq(ID, offerId)));
            if (!offer.getOrder().getCustomer().isActive())
                throw new InactiveCustomerException();

            return purchaseDAO.create(PurchaseBlueprint.from(offerId, employeeId, null));

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
