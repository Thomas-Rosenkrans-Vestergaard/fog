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
    private final List<BomLine>                    lines;
    private final List<? extends BomLineBlueprint> blueprintLines;

    public BomRecord(int id, List<BomLine> lines, List<? extends BomLineBlueprint> blueprintLines)
    {
        this.id = id;
        this.lines = lines;
        this.blueprintLines = blueprintLines;
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
