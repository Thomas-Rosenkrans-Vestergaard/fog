package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutableMaterialList implements MaterialList
{

    private final String title;

    /**
     * The internal storage of the {@link MaterialList}.
     */
    private final List<MaterialLine> lines = new ArrayList<>();

    /**
     * The total price of the entire material list.
     */
    private int total = 0;

    public MutableMaterialList(String title)
    {
        this.title = title;
    }

    /**
     * Adds a new line to the bill using the provided information.
     *
     * @param material The material to add in the line to insert.
     * @param amount   The amount to add in the line to insert.
     * @param notes    The notes to add in the line to insert.
     */
    public void add(Material material, int amount, String notes)
    {
        MaterialLine line = new DefaultMaterialLine(material, amount, notes);
        lines.add(line);
        this.total += line.getTotal();
    }

    /**
     * Returns the title of the material list.
     *
     * @return The title of the material list.
     */
    @Override public String getTitle()
    {
        return title;
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
}
