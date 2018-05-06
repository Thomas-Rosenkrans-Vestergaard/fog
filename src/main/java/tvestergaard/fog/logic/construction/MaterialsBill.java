package tvestergaard.fog.logic.construction;

import java.util.List;

public interface MaterialsBill
{

    /**
     * Adds the provided line to the bill of materials.
     *
     * @param line The line to add to the bill of materials.
     */
    void add(MaterialsBillLine line);

    /**
     * Returns the lines in the bill of materials.
     *
     * @return The lines.
     */
    List<MaterialsBillLine> getLines();

    /**
     * Returns the total of the bill of materials.
     *
     * @return The total of the bill of materials.
     */
    int total();
}
