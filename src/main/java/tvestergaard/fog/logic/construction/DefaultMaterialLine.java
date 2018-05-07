package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

public class DefaultMaterialLine implements MaterialLine
{

    private final Material material;
    private final int      amount;
    private final String   notes;

    public DefaultMaterialLine(Material material, int amount, String notes)
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
    public Material getMaterial()
    {
        return material;
    }

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of the material needed.
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * Returns the notes provided to the customer about the material.
     *
     * @return The notes provided to the customer about the material.
     */
    public String getNotes()
    {
        return notes;
    }

    /**
     * Returns the cost of the line. Taking into consideration the unit size and price of the material.
     *
     * @return The cost of the line.
     */
    public int getTotal()
    {
        return amount / material.getUnit() * material.getPrice();
    }

    @Override public String toString()
    {
        return "DefaultMaterialLine{" +
                "material=" + material +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
