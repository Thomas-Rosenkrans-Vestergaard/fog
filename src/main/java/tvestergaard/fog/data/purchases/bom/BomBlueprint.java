package tvestergaard.fog.data.purchases.bom;

import java.util.List;

public interface BomBlueprint
{

    static BomBlueprint from(List<? extends BomLineBlueprint> lineBlueprints)
    {
        return new BomRecord(-1, null, lineBlueprints);
    }

    /**
     * Returns the lines on the bom specified by the blueprint.
     *
     * @return The lines on the bom specified by the blueprint.
     */
    List<? extends BomLineBlueprint> getBlueprintLines();
}
