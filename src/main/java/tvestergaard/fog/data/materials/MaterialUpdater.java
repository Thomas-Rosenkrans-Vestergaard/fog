package tvestergaard.fog.data.materials;

public interface MaterialUpdater extends MaterialBlueprint
{

    /**
     * Creates a new material updater using the provided information.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param notes       The notes on the material to specify in the updater.
     * @param height      The height of the material to specify in the updater.
     * @param width       The width of the material to specify in the updater.
     * @param usage       The usage of the material to specify in the updater.
     * @return The resulting updater.
     */
    static MaterialUpdater from(int id, String number, String description, String notes, int height, int width, int usage)
    {
        return new MaterialRecord(id, number, description, notes, height, width, usage);
    }

    /**
     * Returns the unique identifier of the material.
     *
     * @return The unique identifier of the material.
     */
    int getId();
}
