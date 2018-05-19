package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.data.materials.SimpleMaterial;

public interface BomLine extends BomLineBlueprint
{

    /**
     * Returns the id of the bom line.
     *
     * @return The id of the bom line.
     */
    int getId();

    /**
     * Returns the material in the bom line.
     *
     * @return The material in the bom line.
     */
    SimpleMaterial getMaterial();

    /**
     * Returns the cost of the line. Taking into consideration the unit size and price of the material.
     *
     * @return The cost of the line.
     */
    default int getTotal()
    {
        int            amount   = getAmount();
        SimpleMaterial material = getMaterial();
        int            unit     = material.getUnit();
        if (amount % unit == 0)
            return amount / unit * material.getPrice();

        return (amount / unit + 1) * material.getPrice();
    }
}
