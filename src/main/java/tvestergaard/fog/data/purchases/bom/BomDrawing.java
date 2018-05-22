package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.logic.construction.ConstructionDrawing;

public interface BomDrawing extends BomDrawingBlueprint
{

    /**
     * Creates a new {@link BomDrawing} from the provided information.
     *
     * @param constructionDrawing The construction drawing.
     * @return The resulting {@link BomDrawing}.
     */
    static BomDrawing from(ConstructionDrawing constructionDrawing)
    {
        return new BomDrawingRecord(-1, constructionDrawing.getTitle(), constructionDrawing.getXML());
    }

    /**
     * Returns the id of the drawing.
     *
     * @return The id of the drawing.
     */
    int getId();
}
