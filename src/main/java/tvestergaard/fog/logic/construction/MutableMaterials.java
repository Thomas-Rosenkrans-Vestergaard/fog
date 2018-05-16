package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutableMaterials implements Materials
{

    /**
     * The internal storage of the {@link Materials}.
     */
    private final List<MaterialLine> lines = new ArrayList<>();

    /**
     * The total price of the entire material list.
     */
    private int total = 0;

    /**
     * Adds a new line to the bill using the provided information.
     *
     * @param material The material to add in the line to insert.
     * @param amount   The amount to add in the line to insert.
     * @param notes    The notes to add in the line to insert.
     */
    public void add(Material material, int amount, String notes)
    {
        MaterialLine line = new LineImpl(material, amount, notes);
        lines.add(line);
        this.total += line.getTotal();
    }

    /**
     * Returns the lines on the bill.
     *
     * @return The lines on the bill.
     */
    @Override public List<MaterialLine> getLines()
    {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Returns the total price of the entire list.
     *
     * @return The total price of the entire list.
     */
    @Override public int getTotal()
    {
        return total;
    }

    public static class LineImpl implements MaterialLine
    {

        private final Material material;
        private final int      amount;
        private final String   notes;

        public LineImpl(Material material, int amount, String notes)
        {
            this.material = material;
            this.amount = amount;
            this.notes = notes;
        }

        /**
         * Returns the material.
         *
         * @return The material.
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
            int unit = material.getUnit();
            if (amount % unit == 0)
                return amount / unit * material.getPrice();

            return (amount / unit + 1) * material.getPrice();
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
}
