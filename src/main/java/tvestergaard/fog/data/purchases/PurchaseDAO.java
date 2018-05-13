package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface PurchaseDAO
{

    /**
     * Returns the purchases in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting purchases.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Purchase> get(Constraint<PurchaseColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first purchase matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first purchase matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Purchase first(Constraint<PurchaseColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new purchase into the data storage.
     *
     * @param blueprint The purchase blueprint that contains the information necessary to create the purchase.
     * @return The purchase instance representing the newly created purchase.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Purchase create(PurchaseBlueprint blueprint) throws DataAccessException;

    /**
     * Returns the number of purchases in the data storage.
     *
     * @return The number of purchases in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int size() throws DataAccessException;
}
