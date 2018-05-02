package tvestergaard.fog.logic.offers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferBlueprint;
import tvestergaard.fog.data.offers.OfferColumn;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class OfferFacade
{

    private final OfferDAO offerDAO;
    private final OfferValidator validator = new OfferValidator();

    public OfferFacade(OfferDAO offerDAO)
    {
        this.offerDAO = offerDAO;
    }

    /**
     * Returns the offers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting offers.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Offer> get(Constraint<OfferColumn>... constraints)
    {
        try {
            return offerDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first offer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first offer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Offer first(Constraint<OfferColumn>... constraints)
    {
        try {
            return offerDAO.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new offer into the data storage.
     *
     * @param order    The id of the order the offer is issued for.
     * @param employee The id of the employee placing the offer.
     * @param price    The total cost of the offer.
     * @return The offer instance representing the newly created offer.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Offer create(int order, int employee, int price) throws OfferValidatorException
    {
        Set<OfferError> errors = validator.validate(price);
        if (!errors.isEmpty())
            throw new OfferValidatorException(errors);

        try {
            return offerDAO.create(OfferBlueprint.from(order, employee, price));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
