package tvestergaard.fog.data.roofing;

public interface RoofingUpdater extends RoofingBlueprint
{

    /**
     * Returns a new roofing updater from the provided information.
     *
     * @param id                  The id of the roofing the specify in the updater.
     * @param name                The name of the roofing to specify in the updater.
     * @param description         The description of the roofing to specify in the updater.
     * @param minimumSlope        The minimum slope of the roofing to specify in the updater.
     * @param maximumSlope        The maximum slope of the roofing to specify in the updater.
     * @param active              Whether or not the roofing to specify in the updater can be applied to orders.
     * @return The newly created roofing updater.
     */
    static RoofingUpdater from(int id,
                               String name,
                               String description,
                               int minimumSlope,
                               int maximumSlope,
                               boolean active)
    {
        return new RoofingRecord(id, name, description, minimumSlope, maximumSlope, active);
    }

    /**
     * Returns the unique identifier of the roofing.
     *
     * @return The unique identifier of the roofing.
     */
    int getId();
}
