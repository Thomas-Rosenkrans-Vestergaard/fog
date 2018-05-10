package tvestergaard.fog.logic.construction;

import java.util.List;

public interface ConstructionSummary
{

    /**
     * Returns the material lists containing the materials needed to create the garage.
     *
     * @return The material lists containing the materials needed to create the garage.
     */
    List<MaterialList> getMaterialLists();

    /**
     * Returns the drawings made to visualize the construction.
     *
     * @return The drawings made to visualize the construction.
     */
    List<ConstructionDrawing> getDrawings();

    /**
     * Returns the total price of the construction.
     *
     * @return The total price of the construction.
     */
    int getTotal();
}
