package tvestergaard.fog.data.purchases.bom;

public interface BomLineBlueprint
{

    /**
     * Creates a new {@link BomLineBlueprint} with the provided information.
     *
     * @param material The id of the material to place in the bom line blueprint.
     * @param amount   The amount of the material to place in the bom line blueprint.
     * @param notes    The notes of the bom line blueprint.
     * @return The resulting {@link BomLineBlueprint}.
     */
    static BomLineBlueprint from(int material, int amount, String notes)
    {
        return new BomLineRecord(-1, null, material, amount, notes);
    }

    /**
     * Returns the id of the material in the bom line.
     *
     * @return the id of the material in the bom line.
     */
    int getMaterialId();

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of material needed.
     */
    int getAmount();

    /**
     * Returns the help notes on the component.
     *
     * @return The help notes on the component.
     */
    String getNotes();
}
