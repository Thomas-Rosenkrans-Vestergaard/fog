package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.components.Component;
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
     * Adds a new component line to the mutable materials instance.
     *
     * @param component The component to add.
     * @param amount    The amount of the component to add.
     */
    public void add(Component component, int amount)
    {
        add(component, amount, component.getNotes());
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

        /**
         * The material specified in the line.
         */
        private final Material material;

        /**
         * The amount of material needed.
         */
        private final int amount;

        /**
         * The notes on the usage of the material.
         */
        private final String notes;

        /**
         * Creates a new {@link LineImpl}.
         *
         * @param material The material specified in the line.
         * @param amount   The amount of material needed.
         * @param notes    The notes on the usage of the material.
         */
        public LineImpl(Material material, int amount, String notes)
        {
            int unit = material.getUnit();

            if (unit > 1) {
                amount = amount % unit != 0 ? amount / unit + 1 : amount / unit;
            }

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
