package tvestergaard.fog.logic.construction;

import java.util.List;

public interface MaterialList
{

    /**
     * Returns the lines on the bill.
     *
     * @return The lines on the bill.
     */
    List<MaterialLine> getLines();

    /**
     * Returns the total price of the entire list.
     *
     * @return The total price of the entire list.
     */
    int getTotal();
}
