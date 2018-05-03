package tvestergaard.fog.data.contracts;

import tvestergaard.fog.data.bom.Bom;
import tvestergaard.fog.data.bom.BomBlueprint;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.offers.Offer;

import java.time.LocalDateTime;
import java.util.Objects;

public class ContractRecord implements Contract
{

    /**
     * The unique identifier of the contract.
     */
    private final int id;

    /**
     * The id of the offer that was accepted.
     */
    private final int offerId;

    /**
     * The offer that was accepted.
     */
    private final Offer offer;

    /**
     * The id of the employee who created the contract.
     */
    private final int employeeId;

    /**
     * The employee who created the contract.
     */
    private final Employee employee;

    /**
     * The blueprint specifying bill of materials described by the contract.
     */
    private final BomBlueprint bomBlueprint;

    /**
     * The bill of materials described by the contract.
     */
    private final Bom bom;

    /**
     * The moment in time when the contract was created.
     */
    private final LocalDateTime createdAt;

    /**
     * Creates a new {@link ContractRecord}.
     *
     * @param id         The unique identifier of the contract.
     * @param offerId    The id of the offer that was accepted.
     * @param offer      The offer that was accepted.
     * @param employeeId The id of the employee who created the contract.
     * @param employee   The employee who created the contract.
     * @param bom        The bill of materials described by the contract.
     * @param createdAt  The moment in time when the contract was created.
     */
    public ContractRecord(int id, int offerId, Offer offer, int employeeId, Employee employee, BomBlueprint bomBlueprint, Bom bom, LocalDateTime createdAt)
    {
        this.id = id;
        this.offerId = offerId;
        this.offer = offer;
        this.employeeId = employeeId;
        this.employee = employee;
        this.bomBlueprint = bomBlueprint;
        this.bom = bom;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the contract.
     *
     * @return The unique identifier of the contract.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the id of the offer that was accepted.
     *
     * @return The id of the offer that was accepted.
     */
    @Override public int getOfferId()
    {
        return offerId;
    }

    /**
     * Returns the offer that was accepted.
     *
     * @return The offer that was accepted.
     */
    @Override public Offer getOffer()
    {
        return offer;
    }

    /**
     * Returns the id of the employee who created the contract.
     *
     * @return The id of the employee who created the contract.
     */
    @Override public int getEmployeeId()
    {
        return employeeId;
    }

    /**
     * Returns the employee who created the contract.
     *
     * @return The employee who created the contract.
     */
    @Override public Employee getEmployee()
    {
        return employee;
    }

    /**
     * Returns the bill of materials described by the contract.
     *
     * @return The bill of materials described by the contract.
     */
    @Override public Bom getBOM()
    {
        return bom;
    }

    /**
     * Returns a blueprint of the the bill of materials within the contract.
     *
     * @return a blueprint of the The bill of materials within the contract.
     */
    @Override public BomBlueprint getBomBlueprint()
    {
        return bomBlueprint;
    }

    /**
     * Returns the moment in time when the contract was created.
     *
     * @return The moment in time when the contract was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;
        Contract that = (Contract) o;
        return getId() == that.getId() &&
                getOfferId() == that.getOfferId() &&
                getEmployeeId() == that.getEmployeeId() &&
                Objects.equals(getOffer(), that.getOffer()) &&
                Objects.equals(getEmployee(), that.getEmployee()) &&
                Objects.equals(getBomBlueprint(), that.getBomBlueprint()) &&
                Objects.equals(bom, that.getBOM()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getOfferId(), getOffer(), getEmployeeId(), getEmployee(), getBomBlueprint(), bom, getCreatedAt());
    }
}
