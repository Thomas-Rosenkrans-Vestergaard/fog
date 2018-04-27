package tvestergaard.fog.data.roofing;

public interface Roofing extends RoofingUpdater
{

    /**
     * Returns a new roofing blueprint from the provided information.
     *
     * @param name                The name of the roofing to specify in the blueprint.
     * @param description         The description of the roofing to specify in the blueprint.
     * @param minimumSlope        The minimum slope of the roofing to specify in the blueprint.
     * @param maximumSlope        The maximum slope of the roofing to specify in the blueprint.
     * @param pricePerSquareMeter The price of the roofing to specify in the blueprint.
     * @param active              Whether or not the roofing to specify in the blueprint can be applied to orders.
     * @return The newly created roofing blueprint.
     */
    static RoofingBlueprint blueprint(String name,
                                      String description,
                                      int minimumSlope,
                                      int maximumSlope,
                                      int pricePerSquareMeter,
                                      boolean active)
    {
        return new RoofingRecord(-1, name, description, minimumSlope, maximumSlope, pricePerSquareMeter, active);
    }

    /**
     * Returns a new roofing updater from the provided information.
     *
     * @param id                  The id of the roofing the specify in the updater.
     * @param name                The name of the roofing to specify in the updater.
     * @param description         The description of the roofing to specify in the updater.
     * @param minimumSlope        The minimum slope of the roofing to specify in the updater.
     * @param maximumSlope        The maximum slope of the roofing to specify in the updater.
     * @param pricePerSquareMeter The price of the roofing to specify in the updater.
     * @param active              Whether or not the roofing to specify in the updater can be applied to orders.
     * @return The newly created roofing updater.
     */
    static RoofingUpdater updater(int id,
                                  String name,
                                  String description,
                                  int minimumSlope,
                                  int maximumSlope,
                                  int pricePerSquareMeter,
                                  boolean active)
    {
        return new RoofingRecord(id, name, description, minimumSlope, maximumSlope, pricePerSquareMeter, active);
    }

    /**
     * Checks that this roofing equals another provided object. The two objects are only considered equal when all the
     * attributes of the two roofings are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the roofing.
     *
     * @return The id of the roofing.
     */
    int hashCode();
}
