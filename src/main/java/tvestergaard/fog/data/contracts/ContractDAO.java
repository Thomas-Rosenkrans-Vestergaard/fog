package tvestergaard.fog.data.contracts;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.contracts.Contract;
import tvestergaard.fog.data.contracts.ContractColumn;

import java.util.List;

public interface ContractDAO
{

    /**
     * Returns the contracts in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting contracts.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Contract> get(Constraint<ContractColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first contract matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first contract matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Contract first(Constraint<ContractColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new contract into the data storage.
     *
     * @param blueprint The contract blueprint that contains the information necessary to create the contract.
     * @return The contract instance representing the newly created contract.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Contract create(ContractBlueprint blueprint) throws DataAccessException;
}
