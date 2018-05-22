package tvestergaard.fog.data.purchases.bom;

import java.util.List;

public interface BomBlueprint
{

    /**
     * Creates a new {@link BomBlueprint} from the provided information.
     *
     * @param lines    The lines to place in the bom blueprint.
     * @param drawings The drawings to place in the bom blueprint.
     * @return The resulting bom blueprint.
     */
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

    /**
     * Returns the drawings on the bom specified by the blueprint.
     *
     * @return The drawings on the bom specified by the blueprint.
     */
    List<? extends BomDrawingBlueprint> getBlueprintDrawings();
}
