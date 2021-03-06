package tvestergaard.fog.data.offers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.purchases.PurchaseBlueprint;
import tvestergaard.fog.data.purchases.PurchaseDAO;
import tvestergaard.fog.data.purchases.bom.BomBlueprint;

import java.util.List;

public interface OfferDAO
{

    /**
     * Returns the offers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting offers.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Offer> get(Constraints<OfferColumn> constraints) throws DataAccessException;

    /**
     * Returns the offers issued to the order with the provided id.
     *
     * @param order The id of the order to return the related offers of.
     * @return The offers related to the order with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List get(int order) throws DataAccessException;

    /**
     * Returns the first offer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first offer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Offer first(Constraints<OfferColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new offer into the data storage.
     *
     * @param blueprint The offer blueprint that contains the information necessary to create the offer.
     * @return The offer instance representing the newly created offer.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Offer create(OfferBlueprint blueprint) throws DataAccessException;

    /**
     * Rejects the offer with the provided id.
     * <p>
     * To accept an offer, use the {@link PurchaseDAO#create(PurchaseBlueprint, BomBlueprint)} method,
     * that will mark the provided offer accepted.
     *
     * @param offerId The id of the offer to mark reject.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    void reject(int offerId) throws DataAccessException;

    /**
     * Returns the number of open offers for the provided customer.
     *
     * @param customer The customer to return the number of open offers for.
     * @return The number of open offers for the provided customer.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int getNumberOfOpenOffers(int customer) throws DataAccessException;
}
