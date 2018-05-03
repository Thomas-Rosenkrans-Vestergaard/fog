package tvestergaard.fog.data.contracts;

import tvestergaard.fog.data.bom.BomBlueprint;

public interface ContractBlueprint
{

    /**
     * Returns the unique identifier of the contract.
     *
     * @return The unique identifier of the contract.
     */
    int getId();

    /**
     * Returns the id of the offer that was accepted.
     *
     * @return The id of the offer that was accepted.
     */
    int getOfferId();

    /**
     * Returns the id of the employee who created the contract.
     *
     * @return The id of the employee who created the contract.
     */
    int getEmployeeId();

    /**
     * Returns a blueprint of the the bill of materials within the contract.
     *
     * @return a blueprint of the The bill of materials within the contract.
     */
    BomBlueprint getBomBlueprint();
}
