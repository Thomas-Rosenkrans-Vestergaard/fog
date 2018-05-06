package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

public class MaterialsBillLineImpl implements MaterialsBillLine
{

    private final Material material;
    private final int      amount;
    private final String   notes;

    public MaterialsBillLineImpl(Material material, int amount, String notes)
    {
        this.material = material;
        this.amount = amount;
        this.notes = notes;
    }

    /**
     * Returns the the material.
     *
     * @return The the material.
     */
    @Override public Material getMaterial()
    {
        return material;
    }

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of the material needed.
     */
    @Override public int getAmount()
    {
        return amount;
    }

    /**
     * Returns the notes provided to the customer about the material.
     *
     * @return The notes provided to the customer about the material.
     */
    @Override public String getNotes()
    {
        return notes;
    }

    /**
     * Returns the cost of the line. Taking into consideration the unit size of the material.
     *
     * @return The cost of the line.
     */
    @Override public int getCost()
    {
        return ((int) Math.ceil((double) amount / material.getUnit())) * material.getPrice();
    }

    @Override public String toString()
    {
        return "MaterialsBillLine{" +
                "material=" + material +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
