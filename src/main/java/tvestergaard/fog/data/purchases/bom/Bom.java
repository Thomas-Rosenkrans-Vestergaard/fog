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
     * Returns the lines on the bom specified by the blueprint.
     *
     * @return The lines on the bom specified by the blueprint.
     */
    List<BomLine> getLines();
}
