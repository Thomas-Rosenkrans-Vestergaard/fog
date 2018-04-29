package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

public interface Shed extends ShedUpdater
{

    /**
     * Returns a new roofing blueprint from the provided information.
     *
     * @param width    The width of the roofing to specify in the blueprint.
     * @param depth    The depth of the roofing to specify in the blueprint.
     * @param cladding The cladding of the roofing to specify in the blueprint.
     * @param flooring The flooring of the roofing to specify in the blueprint.
     * @return The newly created roofing blueprint.
     */
    static ShedBlueprint blueprint(int width, int depth, Cladding cladding, Flooring flooring)
    {
        return new ShedRecord(-1, width, depth, cladding, flooring);
    }

    /**
     * Returns a new roofing blueprint from the provided information.
     *
     * @param id       The id of the roofing to update.
     * @param width    The width of the roofing to specify in the blueprint.
     * @param depth    The depth of the roofing to specify in the blueprint.
     * @param cladding The cladding of the roofing to specify in the blueprint.
     * @param flooring The flooring of the roofing to specify in the blueprint.
     * @return The newly created roofing blueprint.
     */
    static ShedBlueprint updater(int id, int width, int depth, Cladding cladding, Flooring flooring)
    {
        return new ShedRecord(id, width, depth, cladding, flooring);
    }

    /**
     * Checks that this shed equals another provided object. The two objects are only considered equal when all the
     * attributes of the two sheds are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the shed.
     *
     * @return The id of the shed.
     */
    int hashCode();
}
