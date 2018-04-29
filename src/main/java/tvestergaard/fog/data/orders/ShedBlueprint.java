package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

public interface ShedBlueprint
{

    /**
     * Returns the width of the shed.
     *
     * @return The width of the shed.
     */
    int getWidth();

    /**
     * Sets the width of the shed.
     *
     * @param width The new width.
     */
    void setWidth(int width);

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
     * Returns the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    Cladding getCladding();

    /**
     * Sets hte cladding used on the shed.
     *
     * @param cladding The new cladding.
     */
    void setCladding(Cladding cladding);

    /**
     * Returns the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    Flooring getFlooring();

    /**
     * Sets the flooring used on the shed.
     *
     * @param flooring The new flooring.
     */
    void setFlooring(Flooring flooring);
}
