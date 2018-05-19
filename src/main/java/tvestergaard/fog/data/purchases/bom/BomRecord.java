package tvestergaard.fog.data.purchases.bom;

import java.util.List;

public class BomRecord implements Bom
{

    /**
     * The id of the bom.
     */
    private final int id;

    /**
     * The lines on the bom specified by the blueprint.
     */
    private final List<BomLine>             lines;
    private final List<? extends BomLineBlueprint>    blueprintLines;
    private final List<BomDrawing>          drawings;
    private final List<? extends BomDrawingBlueprint> blueprintDrawings;

    public BomRecord(int id, List<BomLine> lines, List<? extends BomLineBlueprint> blueprintLines, List<BomDrawing> drawings, List<? extends BomDrawingBlueprint> blueprintDrawings)
    {
        this.id = id;
        this.lines = lines;
        this.blueprintLines = blueprintLines;
        this.drawings = drawings;
        this.blueprintDrawings = blueprintDrawings;
    }

    /**
     * Returns the drawings in the bom.
     *
     * @return The drawings in the bom.
     */
    @Override public List<BomDrawing> getDrawings()
    {
        return drawings;
    }

    @Override public List<? extends BomDrawingBlueprint> getBlueprintDrawings()
    {
        return blueprintDrawings;
    }

    /**
     * Returns the id of the bom.
     *
     * @return The id of the bom.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the lines on the bom specified by the blueprint.
     *
     * @return The lines on the bom specified by the blueprint.
     */
    @Override public List<BomLine> getLines()
    {
        return lines;
    }

    /**
     * Returns the lines on the bom specified by the blueprint.
     *
     * @return The lines on the bom specified by the blueprint.
     */
    @Override public List<? extends BomLineBlueprint> getBlueprintLines()
    {
        return blueprintLines;
    }
}
