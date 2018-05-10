package tvestergaard.fog.logic.construction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutableConstructionSummary implements ConstructionSummary
{

    private final List<MaterialList>        materialLists        = new ArrayList<>();
    private final List<ConstructionDrawing> constructionDrawings = new ArrayList<>();
    private       int                       total                = 0;

    public void add(MaterialList materialList)
    {
        total += materialList.getTotal();
        materialLists.add(materialList);
    }

    public void add(ConstructionDrawing drawing)
    {
        constructionDrawings.add(drawing);
    }

    /**
     * Returns the material lists containing the materials needed to create the garage.
     *
     * @return The material lists containing the materials needed to create the garage.
     */
    @Override public List<MaterialList> getMaterialLists()
    {
        return Collections.unmodifiableList(materialLists);
    }

    /**
     * Returns the drawings made to visualize the construction.
     *
     * @return The drawings made to visualize the construction.
     */
    @Override public List<ConstructionDrawing> getDrawings()
    {
        return Collections.unmodifiableList(constructionDrawings);
    }

    /**
     * Returns the total price of the construction.
     *
     * @return The total price of the construction.
     */
    @Override public int getTotal()
    {
        return total;
    }
}
