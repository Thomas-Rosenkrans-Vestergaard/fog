package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.logic.construction.ConstructionDrawing;

public interface BomDrawing extends BomDrawingBlueprint
{

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
