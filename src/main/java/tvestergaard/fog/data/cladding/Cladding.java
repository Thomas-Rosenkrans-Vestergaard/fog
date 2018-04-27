package tvestergaard.fog.data.cladding;

public interface Cladding extends CladdingUpdater
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
    static CladdingUpdater updater(int id, String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new CladdingRecord(id, name, description, pricePerSquareMeter, active);
    }

    /**
     * Returns a new cladding blueprint from the provided information.
     *
     * @param name                The name of the cladding to specify in the blueprint.
     * @param description         The description of the cladding to specify in the blueprint.
     * @param pricePerSquareMeter The price of the cladding to specify in the blueprint.
     * @param active              Whether or not the cladding to specify in the blueprint can be applied to orders.
     * @return The newly created cladding blueprint.
     */
    static CladdingBlueprint blueprint(String name, String description, int pricePerSquareMeter, boolean active)
    {
        return new CladdingRecord(-1, name, description, pricePerSquareMeter, active);
    }

    /**
     * Checks that this cladding equals another provided object. The two objects are considered equal when all the
     * attributes of the instances are equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the cladding.
     *
     * @return The id of the cladding.
     */
    int hashCode();
}
