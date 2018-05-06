package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

public interface MaterialsBillLine
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
     * Returns the cost of the line. Taking into consideration the unit size of the material.
     *
     * @return The cost of the line.
     */
    int getCost();
}
