package tvestergaard.fog.logic.construction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaterialsBillImpl implements MaterialsBill
{

    /**
     * The lines in the bill of materials.
     */
    private final List<MaterialsBillLine> lines = new ArrayList<>();

    /**
     * The total of the bill of materials.
     */
    private int total = 0;

    /**
     * Adds the provided line to the bill of materials.
     *
     * @param line The line to add to the bill of materials.
     */
    @Override public void add(MaterialsBillLine line)
    {
        lines.add(line);
        total += line.getCost();
    }

    /**
     * Returns the lines in the bill of materials.
     *
     * @return The lines.
     */
    @Override public List<MaterialsBillLine> getLines()
    {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Returns the total of the bill of materials.
     *
     * @return The total of the bill of materials.
     */
    @Override public int total()
    {
        return total;
    }
}
