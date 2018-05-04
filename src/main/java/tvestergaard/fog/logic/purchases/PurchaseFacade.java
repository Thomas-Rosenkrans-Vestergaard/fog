package tvestergaard.fog.logic.purchases;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeDAO;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseBlueprint;
import tvestergaard.fog.data.purchases.PurchaseDAO;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.offers.OfferColumn.ID;

public class PurchaseFacade
{

    private final PurchaseDAO purchaseDAO;
    private final OfferDAO    offerDAO;
    private final EmployeeDAO employeeDAO;

    public PurchaseFacade(PurchaseDAO purchaseDAO, OfferDAO offerDAO, EmployeeDAO employeeDAO)
    {
        this.purchaseDAO = purchaseDAO;
        this.offerDAO = offerDAO;
        this.employeeDAO = employeeDAO;
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
     *                                          role {@link tvestergaard.fog.data.employees.Role#SALESMAN}.
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
