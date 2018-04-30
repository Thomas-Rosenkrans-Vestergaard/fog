package tvestergaard.fog.data.flooring;

public interface FlooringUpdater extends FlooringBlueprint
{

    /**
     * Returns a new flooring blueprint from the provided information.
     *
     * @param id                  The id of the flooring to specify in the blueprint.
     * @param name                The name of the flooring to specify in the blueprint.
     * @param description         The description of the flooring to specify in the blueprint.
     * @param pricePerSquareMeter The price of the flooring to specify in the blueprint.
     * @param active              Whether or not the flooring to specify in the blueprint can be applied to orders.
     * @return The newly created flooring blueprint.
     */
    static FlooringUpdater from(int id, String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new FlooringRecord(id, name, description, pricePerSquareMeter, active);
    }

    /**
     * Returns the unique identifier of the flooring.
     *
     * @return The unique identifier of the flooring.
     */
    int getId();
}
