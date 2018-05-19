package tvestergaard.fog.data.purchases.bom;

import java.util.List;

public interface Bom extends BomBlueprint
{

    /**
     * Returns the id of the bom.
     *
     * @return The id of the bom.
     */
    int getId();

    /**
     * Returns the lines in the bom.
     *
     * @return The lines in the bom.
     */
    List<BomLine> getLines();

    /**
     * Returns the drawings in the bom.
     *
     * @return The drawings in the bom.
     */
    List<BomDrawing> getDrawings();
}
