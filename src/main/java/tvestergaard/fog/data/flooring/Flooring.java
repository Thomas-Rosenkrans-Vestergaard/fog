package tvestergaard.fog.data.flooring;

public interface Flooring extends FlooringUpdater
{

    /**
     * Returns a new flooring updater from the provided information.
     *
     * @param name                The name of the flooring to specify in the updater.
     * @param description         The description of the flooring to specify in the updater.
     * @param pricePerSquareMeter The price of the flooring to specify in the updater.
     * @param active              Whether or not the flooring to specify in the updater can be applied to orders.
     * @return The newly created flooring updater.
     */
    static FlooringUpdater updater(String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new FlooringRecord(-1, name, description, pricePerSquareMeter, active);
    }

    /**
     * Returns a new flooring blueprint from the provided information.
     *
     * @param name                The name of the flooring to specify in the blueprint.
     * @param description         The description of the flooring to specify in the blueprint.
     * @param pricePerSquareMeter The price of the flooring to specify in the blueprint.
     * @param active              Whether or not the flooring to specify in the blueprint can be applied to orders.
     * @return The newly created flooring blueprint.
     */
    static FlooringBlueprint blueprint(String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new FlooringRecord(-1, name, description, pricePerSquareMeter, active);
    }

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
    static FlooringUpdater updater(int id, String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new FlooringRecord(id, name, description, pricePerSquareMeter, active);
    }

    /**
     * Checks that this flooring equals another provided object. The two objects are only considered equal when all the
     * attributes of the two floorings are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the flooring.
     *
     * @return The id of the flooring.
     */
    int hashCode();
}
