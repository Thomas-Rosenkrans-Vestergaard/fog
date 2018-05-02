package tvestergaard.fog.data.materials;

public interface MaterialUpdater extends MaterialBlueprint
{

    /**
     * Creates a new material updater using the provided information.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to specify in the updater.
     * @param description The material description to specify in the updater.
     * @param price       The price of the material.
     * @param unit        The type of unit the material is in.
     * @param width       The width of the material to specify in the updater.
     * @param height      The height of the material to specify in the updater.
     * @return The resulting updater.
     */
    static MaterialUpdater from(int id, String number, String description, int price, int unit, int width, int height)
    {
        return new MaterialRecord(id, number, description, price, unit, width, height);
    }

    /**
     * Returns the unique identifier of the material.
     *
     * @return The unique identifier of the material.
     */
    int getId();
}
