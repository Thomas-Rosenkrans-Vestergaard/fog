package tvestergaard.fog.data.customers;

public interface CustomerUpdater extends CustomerBlueprint
{

    /**
     * Returns the unique identifier of the customer.
     *
     * @return The unique identifier of the customer.
     */
    int getId();
}
