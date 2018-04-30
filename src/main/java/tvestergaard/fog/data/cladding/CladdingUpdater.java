package tvestergaard.fog.data.cladding;

public interface CladdingUpdater extends CladdingBlueprint
{

    /**
     * Returns a new cladding updater from the provided information.
     *
     * @param id                  The id of the cladding to specify in the updater.
     * @param name                The name of the cladding to specify in the updater.
     * @param description         The description of the cladding to specify in the updater.
     * @param pricePerSquareMeter The price of the cladding to specify in the updater.
     * @param active              Whether or not the cladding to specify in the updater can be applied to orders.
     * @return The newly created cladding updater.
     */
    static CladdingUpdater from(int id, String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new CladdingRecord(id, name, description, pricePerSquareMeter, active);
    }

    /**
     * Returns the unique identifier of the cladding.
     *
     * @return The unique identifier of the cladding.
     */
    int getId();
}
