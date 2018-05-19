package tvestergaard.fog.data.purchases.bom;

import java.util.List;

public interface BomBlueprint
{

    static BomBlueprint from(List<BomLineBlueprint> lines, List<BomDrawingBlueprint> drawings)
    {
        return new BomRecord(-1, null, lines, null, drawings);
    }

    /**
     * Returns the lines on the bom specified by the blueprint.
     *
     * @return The lines on the bom specified by the blueprint.
     */
    List<? extends BomLineBlueprint> getBlueprintLines();

    List<? extends BomDrawingBlueprint> getBlueprintDrawings();
}
