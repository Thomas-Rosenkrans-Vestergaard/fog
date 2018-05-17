package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.data.materials.SimpleMaterial;

public interface BomLine extends BomLineBlueprint
{

    /**
     * Returns the material in the bom line.
     *
     * @return The material in the bom line.
     */
    SimpleMaterial getMaterial();
}
