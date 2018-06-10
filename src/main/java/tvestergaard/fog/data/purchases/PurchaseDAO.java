package tvestergaard.fog.data.purchases;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.purchases.bom.BomBlueprint;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.purchases.PurchaseColumn.ID;

public interface PurchaseDAO
{

    /**
     * Returns the purchases in the data storage.
     *
     * @return The resulting purchases.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default List<Purchase> get() throws DataAccessException
    {
        return get(new Constraints<>());
    }

    /**
     * Returns the purchase with the provided id.
     *
     * @param id The id of the purchase to return.
     * @return The purchase with the provided id. Returns {@code null} if no such purchase exists.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    default Purchase get(int id) throws DataAccessException
    {
        return first(where(eq(ID, id)));
    }

    /**
     * Returns the purchases in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting purchases.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Purchase> get(Constraints<PurchaseColumn> constraints) throws DataAccessException;

    /**
     * Returns the first purchase matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first purchase matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Purchase first(Constraints<PurchaseColumn> constraints) throws DataAccessException;

    /**
     * Inserts a new purchase into the data storage.
     *
     * @param blueprint The purchase blueprint that contains the information necessary to create the purchase.
     * @param bom       The bill of materials to include with the purchase.
     * @return The purchase instance representing the newly created purchase.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Purchase create(PurchaseBlueprint blueprint, BomBlueprint bom) throws DataAccessException;
}
