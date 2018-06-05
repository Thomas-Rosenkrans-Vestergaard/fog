package tvestergaard.fog.logic.purchases;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.purchases.PurchaseBlueprint;
import tvestergaard.fog.data.purchases.PurchaseColumn;
import tvestergaard.fog.data.purchases.PurchaseDAO;
import tvestergaard.fog.data.purchases.bom.*;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.construction.GarageConstructionSummary;
import tvestergaard.fog.logic.construction.MaterialLine;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;
import tvestergaard.fog.logic.offers.OfferNotOpenException;
import tvestergaard.fog.logic.offers.UnknownOfferException;

import java.util.ArrayList;
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
     * The dao used to access bills of materials in data storage.
     */
    private final BomDAO bomDAO;

    /**
     * The offer dao used to access the offers in the application.
     */
    private final OfferDAO offerDAO;

    /**
     * The construction facade that constructs the purchased garage.
     */
    private final ConstructionFacade constructionFacade;

    /**
     * Creates a new {@link PurchaseFacade}.
     *
     * @param purchaseDAO        The purchase dao to use to access the purchases in the application.
     * @param offerDAO           The offer dao used to access the offers in the application.
     * @param bomDAO             The dao used to access bills of materials in data storage.
     * @param constructionFacade The construction facade that constructs the purchased garage.
     */
    public PurchaseFacade(PurchaseDAO purchaseDAO, OfferDAO offerDAO, BomDAO bomDAO, ConstructionFacade constructionFacade)
    {
        this.purchaseDAO = purchaseDAO;
        this.offerDAO = offerDAO;
        this.bomDAO = bomDAO;
        this.constructionFacade = constructionFacade;
    }

    /**
     * Returns the purchases in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting purchases.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Purchase> get(Constraints<PurchaseColumn> constraints)
    {
        try {
            return purchaseDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the purchases in the data storage.
     *
     * @return The resulting purchases.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Purchase> get()
    {
        try {
            return purchaseDAO.get();
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
    public Purchase first(Constraints<PurchaseColumn> constraints)
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
     * @param offerId The id of the offer from which to create the purchase.
     * @return An entity representing the new purchase.
     * @throws InactiveCustomerException        When the customer that the offer was issued to is inactive, and can therefor
     *                                          not create purchases.
     * @throws InsufficientPermissionsException When the employee attempting to create the purchase, is not a member of
     *                                          role {@link Role#SALESMAN}.
     * @throws UnknownOfferException            When the provided offer id is unknown to the application.
     * @throws OfferNotOpenException            When the offer is not open, and a purchase cannot be created from it.
     */
    public Purchase create(int offerId) throws InactiveCustomerException,
                                               InsufficientPermissionsException,
                                               UnknownOfferException,
                                               OfferNotOpenException
    {
        try {
            Offer offer = offerDAO.first(where(eq(ID, offerId)));
            if (offer == null)
                throw new UnknownOfferException();
            if (!offer.isOpen())
                throw new OfferNotOpenException();
            if (!offer.getOrder().getCustomer().isActive())
                throw new InactiveCustomerException();

            Employee employee = offer.getEmployee();
            if (!employee.is(Role.SALESMAN))
                throw new InsufficientPermissionsException(Role.SALESMAN);

            GarageConstructionSummary constructionSummary = constructionFacade.construct(offer.getOrder());
            List<BomLineBlueprint>    lineBlueprints      = new ArrayList<>();
            for (MaterialLine line : constructionSummary.getSkeletonConstructionSummary().getMaterials().getLines())
                lineBlueprints.add(BomLineBlueprint.from(line.getMaterial().getId(), line.getAmount(), line.getNotes()));
            for (MaterialLine line : constructionSummary.getRoofingConstructionSummary().getMaterials().getLines())
                lineBlueprints.add(BomLineBlueprint.from(line.getMaterial().getId(), line.getAmount(), line.getNotes()));

            List<BomDrawingBlueprint> drawings = new ArrayList<>();
            drawings.add(BomDrawing.from(constructionSummary.getSkeletonConstructionSummary().getAerialView()));
            drawings.add(BomDrawing.from(constructionSummary.getSkeletonConstructionSummary().getSideView()));
            drawings.add(BomDrawing.from(constructionSummary.getRoofingConstructionSummary().getAerialSkeletonView()));
            drawings.add(BomDrawing.from(constructionSummary.getRoofingConstructionSummary().getAerialTiledView()));

            return purchaseDAO.create(PurchaseBlueprint.from(offerId), BomBlueprint.from(lineBlueprints, drawings));

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the bom with the provided id.
     *
     * @param id The id of the bom to retrieve.
     * @return The bom instance representing the retrieved bom. {@code null} in case no bom with the provided id exists.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Bom getBom(int id)
    {
        try {
            return bomDAO.get(id);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
