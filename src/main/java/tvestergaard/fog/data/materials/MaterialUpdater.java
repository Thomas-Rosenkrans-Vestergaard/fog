package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.materials.attributes.AttributeValue;

import java.util.Set;

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
     * @param category    The category the material belongs to.
     * @param attributes  The attributes to add to the material.
     * @return The resulting updater.
     */
    static MaterialUpdater from(int id, String number, String description, int price, int unit, int category, Set<AttributeValue> attributes)
    {
        return new MaterialRecord(id, number, description, price, unit, true, category, null, attributes);
    }

    /**
     * Returns the unique identifier of the material.
     *
     * @return The unique identifier of the material.
     */
    int getId();
}
