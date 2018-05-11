package tvestergaard.fog.data.orders;

public interface ShedBlueprint
{

    /**
     * Returns a new roofing blueprint from the provided information.
     *
     * @param depth    The depth of the roofing to specify in the blueprint.
     * @param cladding The cladding of the roofing to specify in the blueprint.
     * @param flooring The flooring of the roofing to specify in the blueprint.
     * @return The newly created roofing blueprint.
     */
    static ShedBlueprint from(int depth, int cladding, int flooring)
    {
        return new ShedRecord(-1, depth, cladding, null, flooring, null);
    }

    /**
     * Returns the depth of the shed.
     *
     * @return The depth of the shed.
     */
    int getDepth();

    /**
     * Sets the depth of the shed.
     *
     * @param depth The new depth.
     */
    void setDepth(int depth);

    /**
     * Returns the id of the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    int getCladdingId();

    /**
     * Sets the id of the cladding used on the shed.
     *
     * @param cladding The new cladding.
     */
    void setCladdingId(int cladding);

    /**
     * Returns the id of the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    int getFlooringId();

    /**
     * Sets the id of the flooring used on the shed.
     *
     * @param flooring The new flooring.
     */
    void setFlooringId(int flooring);
}
