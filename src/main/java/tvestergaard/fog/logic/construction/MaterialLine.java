package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

public interface MaterialLine
{

    /**
     * Returns the the material.
     *
     * @return The the material.
     */
    Material getMaterial();

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of the material needed.
     */
    int getAmount();

    /**
     * Returns the notes provided to the customer about the material.
     *
     * @return The notes provided to the customer about the material.
     */
    String getNotes();

    /**
     * Returns the cost of the line. Taking into consideration the unit size and price of the material.
     *
     * @return The cost of the line.
     */
    default int getTotal()
    {
        int      amount   = getAmount();
        Material material = getMaterial();
        int      unit     = material.getUnit();
        if (amount % unit == 0)
            return amount / unit * material.getPrice();

        return (amount / unit + 1) * material.getPrice();
    }
}
