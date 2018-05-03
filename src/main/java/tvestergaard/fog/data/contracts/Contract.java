package tvestergaard.fog.data.contracts;

import tvestergaard.fog.data.bom.Bom;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.offers.Offer;

import java.time.LocalDateTime;

public interface Contract extends ContractBlueprint
{

    /**
     * Returns the offer that was accepted.
     *
     * @return The offer that was accepted.
     */
    Offer getOffer();

    /**
     * Returns the employee who created the contract.
     *
     * @return The employee who created the contract.
     */
    Employee getEmployee();

    /**
     * Returns the bill of materials described by the contract.
     *
     * @return The bill of materials described by the contract.
     */
    Bom getBOM();

    /**
     * Returns the moment in time when the contract was created.
     *
     * @return The moment in time when the contract was created.
     */
    LocalDateTime getCreatedAt();
}
